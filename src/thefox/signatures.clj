(ns thefox.signatures
  (:import [java.security Signature SecureRandom KeyPairGenerator KeyPair]
           [java.io StringReader StringWriter]
           [java.util Base64]
           [org.bouncycastle.openssl PEMReader PEMWriter]))

; TODO: error handling. i'm sure the java bits are gonna shit themselves as
; soon as you give it an invalid string

; Type hint everything until `lein check` shuts up.

(java.security.Security/addProvider
  (org.bouncycastle.jce.provider.BouncyCastleProvider.))

(def algo "SHA256withRSA")

(defn raw-keys
  "Generates raw keys. It's a Java object so don't touch it unless you know
  what you're doing."
  []
  (let [generator (doto (KeyPairGenerator/getInstance "RSA")
                    (.initialize 1024))]
    (.generateKeyPair generator)))

(defn string-to-key
  [input]
  (let [key (-> input StringReader. PEMReader. .readObject)]
    ; a private key is read as a keypair
    (if (instance? KeyPair key)
      (.getPrivate ^KeyPair key)
      key)))

(defn key-to-string
  "Turns a key (private or public) into a string."
  [key]
  (let [string-writer (StringWriter.)
        pem-writer (PEMWriter. string-writer)]
    (.writeObject pem-writer key)
    (.flush pem-writer)
    (.toString string-writer)))

(defn generate-keypair
  "Generates a private-public keypair and returns it in a hashmap of strings."
  []
  (let [^KeyPair keys (raw-keys)]
    { :public (-> keys .getPublic key-to-string)
      :private (-> keys .getPrivate key-to-string)}))

(defn verify
  "Expects a base64 encoded signature"
  [^String signature ^String actual-data public-key]
  (let [sig (doto (Signature/getInstance algo)
                  (.initVerify ^org.bouncycastle.jce.provider.JCERSAPublicKey (string-to-key public-key))
                  (.update (.getBytes actual-data)))]
    (.verify sig
      (.decode (Base64/getDecoder) signature))))

(defn sign
  "Produces a base64 encoded signature"
  [^String data private-key]
  (let [sig (doto (Signature/getInstance algo)
                  (.initSign (string-to-key private-key))
                  (.update (.getBytes data)))]
    (.encodeToString (Base64/getEncoder)
      (.sign sig))))

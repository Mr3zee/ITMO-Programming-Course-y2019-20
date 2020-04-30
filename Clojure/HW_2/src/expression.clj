(defn constant [val] (fn [vars] val))
(defn variable [name] (fn [vars] (get vars name)))
(defn basic-op [f & args] (fn [vars] (apply f ((apply juxt args) vars))))
(def add (partial basic-op +))
(def subtract (partial basic-op -))
(def multiply (partial basic-op *))
(def divide (partial basic-op /))
; todo double divide

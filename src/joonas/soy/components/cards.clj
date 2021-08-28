(ns joonas.soy.components.cards)

(defn card [content]
  [:div {:class "bg-white p-4 rounded border border-orange-200 flex flex-col"}
   content])

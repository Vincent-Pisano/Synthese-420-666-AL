import React, { createContext, useState } from "react";

export const CartContext = createContext();

const CartContextProvider = (props) => {
  //aller chercher la commande en cours d'un client

  const [cart, setCart] = useState({
    client: undefined,
    status: "",
    orderItems: [],
    totalPrice:0
  });
  return (
    <CartContext.Provider value={[ cart, setCart ]}>
      {props.children}
    </CartContext.Provider>
  );
};

export default CartContextProvider;

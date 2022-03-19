import React, { createContext, useState, useEffect } from "react";
import axios from "axios";
import { GET_WAITING_ORDER, SAVE_ORDER } from "../utils/API";
import Auth from "../services/Auth";

export const CartContext = createContext();

const CartContextProvider = (props) => {
  const [cart, setCart] = useState({
    orderItems: [],
    totalPrice: 0,
  });

  useEffect(() => {
    let idClient =
      Auth.user !== undefined && Auth.user !== null
        ? Auth.user.id
        : "undefined";
    axios
      .get(GET_WAITING_ORDER + idClient)
      .then((response) => {
        setCart(response.data);
      })
      .catch((error) => {
        let fields = {
          client: Auth.user,
          orderItems: cart.orderItems,
          totalPrice: cart,
        }
        axios
          .post(SAVE_ORDER, fields)
          .then((response) => {
            setCart(response.data);
          })
          .catch((error) => {
            console.log("Couldn't create the cart's order");
          });
      });
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [cart.temp]);

  return (
    <CartContext.Provider value={[cart, setCart]}>
      {props.children}
    </CartContext.Provider>
  );
};

export default CartContextProvider;

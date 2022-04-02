import React from "react";
import { SAVE_ORDER } from "../../utils/API";
import axios from "axios";
import CartItem from "./CartItem";

const CartTable = ({ cart, setCart }) => {
  function updateQuantityOrderItem(event) {
    let indexOrderItem = cart.orderItems.findIndex(
      (orderItem) => orderItem.item.name === event.target.getAttribute("name")
    );
    let oldOrderItems = cart.orderItems;
    oldOrderItems[indexOrderItem].quantity = event.target.value !== "" ? parseInt(event.target.value) : 0;
    return oldOrderItems;
  }

  function updateOrder(event) {
    let newOrderItems =
      event.target.id === "update"
        ? updateQuantityOrderItem(event)
        : cart.orderItems.filter(
            (orderItem) =>
              orderItem.item.name !== event.currentTarget.getAttribute("name")
          );
    let fields = {
      id: cart.id,
      client: cart.client,
      status: cart.status,
      orderItems: newOrderItems,
      totalPrice: newOrderItems
        .map((orderItem) => orderItem.item.price * orderItem.quantity)
        .reduce((prev, curr) => prev + curr, 0),
      creationDate: cart.creationDate,
      isDisabled: cart.isDisabled,
    };
    axios
      .post(SAVE_ORDER, fields)
      .then((response) => {
        setCart(response.data);
      })
      .catch((error) => {});
  }

  return (
    <div className="table-responsive">
      <table className="table table-image text-white">
        <thead>
          <tr>
            <th scope="col"></th>
            <th scope="col">Produit</th>
            <th scope="col">Prix</th>
            <th scope="col">Quantité</th>
            <th scope="col">Total</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {cart.orderItems.length > 0 ? (
            cart.orderItems.map((orderItem, i) => (
              <CartItem key={i} orderItem={orderItem} updateOrder={updateOrder} />
            ))
          ) : (
            <tr>
              <td colSpan={6}>
                <div className="alert alert-danger mt-3" role="alert">
                  Aucun produit n'est enregristré dans le panier
                </div>
              </td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="d-flex justify-content-end">
        <h5>
          Total: <span className="price text-success">{cart.totalPrice.toFixed(2)}$</span>
        </h5>
      </div>
    </div>
  );
};
export default CartTable;

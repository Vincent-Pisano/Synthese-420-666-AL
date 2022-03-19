import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { GET_ITEM_IMAGE, SAVE_ORDER } from "../../utils/API";
import axios from "axios";

const CartTable = ({ cart, setCart }) => {
  function updateQuantityOrderItem(event) {
    let indexOrderItem = cart.orderItems.findIndex(
      (orderItem) => orderItem.item.name === event.target.getAttribute("name")
    );
    let oldOrderItems = cart.orderItems;
    oldOrderItems[indexOrderItem].quantity = parseInt(event.target.value);
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
              <tr key={i}>
                <td className="w-25 align-middle">
                  <img
                    src={GET_ITEM_IMAGE + orderItem.item.id}
                    className="img-fluid img-thumbnail"
                    alt="produit"
                  />
                </td>
                <td className="align-middle">{orderItem.item.name}</td>
                <td className="align-middle">
                  {orderItem.item.price.toFixed(2)}$
                </td>
                <td className="align-middle qty">
                  <input
                    style={{ maxWidth: "3.5rem" }}
                    type="number"
                    className="form-control"
                    min="1"
                    step="1"
                    name={orderItem.item.name}
                    id="update"
                    value={orderItem.quantity}
                    onChange={(event) => updateOrder(event)}
                  />
                </td>
                <td className="align-middle">
                  {(orderItem.item.price * orderItem.quantity).toFixed(2)}$
                </td>
                <td className="align-middle">
                  <button
                    className="btn btn-danger"
                    name={orderItem.item.name}
                    id="delete"
                    onClick={(event) => updateOrder(event)}
                  >
                    <FontAwesomeIcon className="text-white" icon={faTrashAlt} />
                  </button>
                </td>
              </tr>
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

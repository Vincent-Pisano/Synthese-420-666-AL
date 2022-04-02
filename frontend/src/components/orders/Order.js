import React from "react";
import { ORDER_STATUS } from "../../utils/SECURITY";

const Order = ({ order, onClick }) => {

    function getFormattedDate(date) {
      return ('0' + date.getDate()).slice(-2) +  "/" + ('0' + (date.getMonth() + 1)).slice(-2) + "/" + date.getFullYear()
    }

  return (
    <tr>
      <td className="w-25 align-middle">{getFormattedDate(new Date(order.creationDate))}</td>
      <td className="align-middle">
        {order.totalPrice.toFixed(2)}$
      </td>
      <td className="align-middle">{ORDER_STATUS.find(o => o.value === order.status).name}</td>
      <td className="align-middle">{order.shippingDate !== null ? getFormattedDate(new Date(order.shippingDate)) : "en attente"}</td>
      <td className="align-middle">
        <button
          className="btn btn-success"
          name={order.id}
          id="delete"
          onClick={() => onClick(order)}
        >
          voir la commande
        </button>
      </td>
    </tr>
  );
};
export default Order;

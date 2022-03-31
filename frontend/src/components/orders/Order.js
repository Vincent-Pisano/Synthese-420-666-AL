import React from "react";
import { ORDER_STATUS } from "../../utils/SECURITY";

const Order = ({ order, onClick }) => {

    let date = new Date(order.creationDate);

    console.log()

  return (
    <tr>
      <td className="w-25 align-middle">{('0' + date.getDay()).slice(-2) +  " " + ('0' + date.getMonth()).slice(-2) + " " + date.getFullYear()}</td>
      <td className="align-middle">
        {order.totalPrice.toFixed(2)}$
      </td>
      <td className="align-middle">{ORDER_STATUS.find(o => o.value === order.status).name}</td>
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

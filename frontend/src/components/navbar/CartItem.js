import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { GET_ITEM_IMAGE } from "../../utils/API";

const CartItem = ({ orderItem, updateOrder }) => {
  return (
    <>
      <tr>
        <td className="w-25 align-middle">
          <img
            src={GET_ITEM_IMAGE + orderItem.item.id}
            className="img-fluid img-thumbnail"
            alt="produit"
          />
        </td>
        <td className="align-middle">{orderItem.item.name}</td>
        <td className="align-middle">{orderItem.item.price.toFixed(2)}$</td>
        <td className="align-middle qty">
          <input
            style={{ maxWidth: "5rem" }}
            type="number"
            className="form-control"
            min="1"
            max="100"
            step="1"
            name={orderItem.item.name}
            id="update"
            value={orderItem.quantity}
            onChange={(event) => updateOrder(event)}
            disabled={updateOrder === undefined}
          />
        </td>
        <td className="align-middle">
          {(orderItem.item.price * orderItem.quantity).toFixed(2)}$
        </td>
        {updateOrder !== undefined ?
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
        : <></>
        }
        
      </tr>
    </>
  );
};
export default CartItem;

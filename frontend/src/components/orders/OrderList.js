import axios from "axios";
import React, { useEffect, useState } from "react";
import Auth from "../../services/Auth";
import { GET_ALL_ORDERS_OF_CLIENT } from "../../utils/API";
import {
  ERROR_ORDERS_NOT_FOUND,
  ERROR_SERVER_NOT_FOUND,
} from "../../utils/MESSAGE";
import OrderInfoModal from "./modal/OrderInfoModal";
import Order from "./Order";

const OrderList = () => {
  const [orders, setOrders] = useState([]);
  const [currentOrder, setCurrentOrder] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState([]);

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  useEffect(() => {
    axios
      .get(GET_ALL_ORDERS_OF_CLIENT + Auth.user.id)
      .then((response) => {
        setOrders(response.data);
        setErrorMessage("");
      })
      .catch((error) => {
        if (error.response && error.response.status === 409) {
          setErrorMessage(ERROR_ORDERS_NOT_FOUND);
          setOrders([]);
        } else if (error.request) {
          setErrorMessage(ERROR_SERVER_NOT_FOUND);
        }
      });
  }, []);

  function onOrderClicked(order) {
    setCurrentOrder(order);
    handleShow();
  }

  return (
    <div className="form-dark form-overflow" style={{ paddingBottom: "150px" }}>
      <div
        className="form-dark-form"
        style={{ maxWidth: "800px", padding: "2%" }}
      >
        <h2>Vos commandes</h2>
        <hr />
        <div className="table-responsive">
          <table className="table table-image text-white">
            <thead>
              <tr>
                <th scope="col">Date</th>
                <th scope="col">Prix total</th>
                <th scope="col">Status</th>
                <th scope="col">Informations</th>
              </tr>
            </thead>
            <tbody className="text-center">
              {orders.length > 0 ? (
                orders.map((order, i) => (
                  <Order key={i} order={order} onClick={onOrderClicked} />
                ))
              ) : (
                <div className="alert alert-danger w-100 m-auto" role="alert">
                  {errorMessage}
                </div>
              )}
            </tbody>
          </table>
        </div>
      </div>
      <OrderInfoModal
        show={show}
        handleClose={handleClose}
        currentOrder={currentOrder}
      />
    </div>
  );
};
export default OrderList;

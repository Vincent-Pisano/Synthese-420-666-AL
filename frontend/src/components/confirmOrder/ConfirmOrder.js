import React, { useState, useContext } from "react";
import { CartContext } from "../../contexts/CartContext";
import { useNavigate } from "react-router";
import CartTable from "../navbar/CartTable";
import { useFormFields } from "../../services/FormFields";
import { URL_HOME } from "../../utils/URL";
import { PROVINCES } from "../../utils/SECURITY";
import {
  CONFIRM_ORDER,
  ERROR_CONFIRM_ORDER,
  ERROR_SERVER_NOT_FOUND,
} from "../../utils/MESSAGE";
import { API_CONFIRM_ORDER } from "../../utils/API";
import axios from "axios";
import Auth from "../../services/Auth";

const ConfirmOrder = () => {
  let navigate = useNavigate();
  const [cart, setCart] = useContext(CartContext);
  const [errorMessage, setErrorMessage] = useState("");
  const [hasClicked, setHasClicked] = useState(false);

  const [address, handleFieldChange] = useFormFields({
    address: "",
    city: "",
    province: "Québec",
    apartmentCode: "",
    postalCode: "",
  });

  function onCreatePost(e) {
    setHasClicked(true);
    e.preventDefault();
    let order = {
      ...cart,
      shippingAddress: address,
    };
    axios
      .post(API_CONFIRM_ORDER, order)
      .then((response) => {
        setTimeout(() => {
          setCart({
            temp: Auth.user,
            orderItems: [],
            totalPrice: 0,
          });
          navigate(URL_HOME);
        }, 3000);
        setErrorMessage(CONFIRM_ORDER);
      })
      .catch((error) => {
        setHasClicked(false);
        if (error.response && error.response.status === 409 && error.response.data.length > 0) {
          setErrorMessage(ERROR_CONFIRM_ORDER(error.response.data));
        } else if (error.request) {
          setErrorMessage(ERROR_SERVER_NOT_FOUND);
        }
      });
  }

  function showErrorMessage() {
    if (errorMessage !== "") {
      return (
        <div
          className={
            errorMessage === CONFIRM_ORDER
              ? "mt-5 alert alert-success"
              : "mt-5 alert alert-danger"
          }
          role="alert"
        >
          {errorMessage}
        </div>
      );
    }
  }

  return (
    <div className="form-dark form-overflow" style={{ paddingBottom: "150px" }}>
      <form
        className="form-dark-form"
        style={{ maxWidth: "800px" }}
        onSubmit={onCreatePost}
      >
        <h2 className="mb-5">Confirmer votre commande</h2>
        <CartTable cart={cart} setCart={setCart} />
        <div style={{ width: "75%", margin: "auto" }}>
          <h3 className="mb-3 mt-5">Informations de livraison</h3>
          <div className="form-group my-3">
            <input
              value={address.address}
              onChange={handleFieldChange}
              className="form-control"
              id="address"
              type="text"
              placeholder="Adresse complète"
              required
            />
          </div>
          <div className="form-group my-3">
            <input
              value={address.apartmentCode}
              onChange={handleFieldChange}
              className="form-control"
              id="apartmentCode"
              type="text"
              placeholder="Numéro d'appartment (laisser vide si non applicable)"
            />
          </div>
          <div className="form-group my-3">
            <input
              value={address.postalCode}
              onChange={handleFieldChange}
              className="form-control"
              id="postalCode"
              type="text"
              minLength={6}
              maxLength={6}
              placeholder="Code postal (AAAAAA)"
              required
            />
          </div>
          <div className="form-group my-3">
            <input
              value={address.city}
              onChange={handleFieldChange}
              className="form-control"
              id="city"
              type="text"
              placeholder="Ville"
              required
            />
          </div>
          <div className="form-group my-3">
            <select
              className="form-control"
              defaultValue={address.province}
              onChange={handleFieldChange}
              id="status"
              placeholder="Catégorie"
              required
            >
              {PROVINCES.map((status, key) => {
                return (
                  <option className="text-dark" key={key} value={status.name}>
                    {status.name}
                  </option>
                );
              })}
            </select>
          </div>
        </div>
        {showErrorMessage()}
        <div className="border-top-0 mt-5 d-flex justify-content-between">
          <button
            className="mx-2 btn btn-outline-danger text-right"
            onClick={() => navigate(URL_HOME)}
          >
            <i className="bi-cart-fill me-1"></i>
            fermer
          </button>
          <button
            type="submit"
            className="btn btn-success"
            disabled={
              cart.orderItems === undefined ||
              cart.orderItems.length === 0 ||
              hasClicked
            }
          >
            Confirmer la commande
          </button>
        </div>
      </form>
    </div>
  );
};
export default ConfirmOrder;

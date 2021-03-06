import axios from "axios";
import { React, useState, useContext } from "react";
import { useNavigate } from "react-router";
import { URL_HOME, URL_LOGIN } from "../../utils/URL";
import { useFormFields } from "../../services/FormFields";
import { ERROR_EMAIL_ALREADY_EXISTS, ERROR_SERVER_NOT_FOUND } from "../../utils/MESSAGE";
import { SUBSCRIBE_CLIENT } from "../../utils/API";
import auth from "../../services/Auth";
import { CartContext } from "../../contexts/CartContext";

const Subscribe = () => {
  let navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");
  // eslint-disable-next-line no-unused-vars
  const [ cart, setCart ] = useContext(CartContext)
  const [fields, handleFieldChange] = useFormFields({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  function onCreatePost(e) {
    e.preventDefault();

    axios
      .post(SUBSCRIBE_CLIENT, fields)
      .then((response) => {
        setCart({
          temp: response.data,
          orderItems: [],
          totalPrice:0
        })
        auth.login(() => {
          navigate(URL_HOME);
        }, response.data);
      })
      .catch((error) => {
        if (error.response && error.response.status === 409) {
          setErrorMessage(ERROR_EMAIL_ALREADY_EXISTS);
        } else if (error.request) {
          setErrorMessage(ERROR_SERVER_NOT_FOUND);
        }
      });
  }

  function showErrorMessage() {
    if (errorMessage !== "") {
      return (
        <div className="alert alert-danger" role="alert">
          {errorMessage}
        </div>
      );
    }
  }

  return (
    <div className="form-dark" style={{marginTop: "10%", paddingBottom:"150px"}}>
      <form className="form-dark-form" onSubmit={(e) => onCreatePost(e)}>
        <h2 className="mb-5">Inscription</h2>
        <div className="d-flex justify-content-between">
          <div className="form-group my-2">
            <input
              value={fields.firstName}
              onChange={handleFieldChange}
              className="form-control"
              id="firstName"
              type="text"
              name="firstName"
              placeholder="Pr??nom"
              required
            />
          </div>
          <div className="form-group my-2">
            <input
              value={fields.lastName}
              onChange={handleFieldChange}
              className="form-control"
              id="lastName"
              type="text"
              name="lastName"
              placeholder="Nom"
              required
            />
          </div>
        </div>
        <div className="form-group my-3">
          <input
            value={fields.email}
            onChange={handleFieldChange}
            className="form-control"
            id="email"
            type="email"
            name="email"
            placeholder="Courriel"
            pattern="[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
            required
          />
        </div>
        <div className="form-group my-3">
          <input
            value={fields.password}
            onChange={handleFieldChange}
            id="password"
            className="form-control"
            type="password"
            name="password"
            placeholder="Mot de passe"
            minLength="6"
            required
          />
        </div>
        {showErrorMessage()}
        <div className="form-group">
          <button className="btn btn-outline-light my-3" type="submit">
            Inscription
          </button>
        </div>
        <p className="forgot" onClick={() => navigate(URL_LOGIN)}>
          D??j?? inscrit ?
        </p>
      </form>
    </div>
  );
};
export default Subscribe;

import axios from "axios";
import { React, useState, useContext } from "react";
import { useNavigate } from "react-router";
import { URL_HOME, URL_SUBSCRIBE } from "../../utils/URL";
import { useFormFields } from "../../services/FormFields";
import { ERROR_ACCOUNT_NOT_FOUND, ERROR_SERVER_NOT_FOUND } from "../../utils/MESSAGE";
import { LOGIN_ADMIN, LOGIN_CLIENT } from "../../utils/API";
import { ADMIN_EMAIL } from "../../utils/SECURITY"
import auth from "../../services/Auth";
import { CartContext } from "../../contexts/CartContext";

const Login = () => {
  let navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");
  // eslint-disable-next-line no-unused-vars
  const [ cart, setCart ] = useContext(CartContext)
  const [fields, handleFieldChange] = useFormFields({
    email: "",
    password: "",
  });

  function onCreatePost(e) {
    e.preventDefault();
    var url = fields.email === ADMIN_EMAIL ? LOGIN_ADMIN : LOGIN_CLIENT
      axios
      .get(url + fields.email + "/" + fields.password)
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
          setErrorMessage(ERROR_ACCOUNT_NOT_FOUND);
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
        <h2 className="mb-5">Connexion</h2>
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
            Connexion
          </button>
        </div>
        <p className="forgot"
        onClick={() => navigate(URL_SUBSCRIBE)}>
          Pas encore inscrit ?
        </p>
      </form>
    </div>
  );
};
export default Login;

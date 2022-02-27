import axios from "axios";
import { React, useState } from "react";
import { useNavigate } from "react-router";
import { URL_HOME, URL_SUBSCRIBE } from "../../utils/URL";
import { useFormFields } from "../../services/FormFields";
import { ERROR_ACCOUNT_NOT_FOUND } from "../../utils/MESSAGE";
import { LOGIN_ADMIN, LOGIN_CLIENT } from "../../utils/API";
import { ADMIN_EMAIL } from "../../utils/SECURITY"
import auth from "../../services/Auth";

const Login = () => {
  let navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");

  const [fields, handleFieldChange] = useFormFields({
    email: "",
    password: "",
  });

  function onCreatePost(e) {
    e.preventDefault();

    if (fields.email === ADMIN_EMAIL) {
      axios
      .get(LOGIN_ADMIN + fields.email + "/" + fields.password)
      .then((response) => {
        auth.login(() => {
          navigate(URL_HOME);
        }, response.data);
      })
      .catch((error) => {
        setErrorMessage(ERROR_ACCOUNT_NOT_FOUND);
      });
    } else {
      axios
      .get(LOGIN_CLIENT + fields.email + "/" + fields.password)
      .then((response) => {
        auth.login(() => {
          navigate(URL_HOME);
        }, response.data);
      })
      .catch((error) => {
        setErrorMessage(ERROR_ACCOUNT_NOT_FOUND);
      });
    }
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
    <div className="form-dark" style={{marginTop: "10%"}}>
      <form onSubmit={(e) => onCreatePost(e)}>
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

import axios from "axios";
import { React, useState } from "react";
import { useNavigate } from "react-router";
import { useFormFields } from "../../services/FormFields";
import { SAVE_ITEM } from "../../utils/API";
import { CONFIRM_ADD_ITEM, ERROR_INVALID_ITEM } from "../../utils/MESSAGE";
import { CATEGORIES } from "../../utils/SECURITY";
import { URL_HOME } from "../../utils/URL";

const AddItemForm = () => {
  let navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");
  const [image, setImage] = useState(undefined);

  const [fields, handleFieldChange] = useFormFields({
    name: "",
    description: "",
    price: undefined,
    quantity: undefined,
    category: "OTHER",
  });

  function onCreatePost(e) {
    e.preventDefault();
    if (image !== undefined) {
      addItem();
    } else {
      setErrorMessage("Veuillez ajouter l'image du produit");
    }
  }

  function showErrorMessage() {
    if (errorMessage !== "") {
      return (
        <div
          className={
            errorMessage === CONFIRM_ADD_ITEM
              ? "alert alert-success"
              : "alert alert-danger"
          }
          role="alert"
        >
          {errorMessage}
        </div>
      );
    }
  }

  function showImagePreview() {
    if (image !== undefined) {
      return (
        <img
          src={URL.createObjectURL(image)}
          style={{ height: "100%", width: "100%" }}
          alt="Produit"
        />
      );
    }
  }

  function addItem() {
    let formData = new FormData();
    formData.append("item", JSON.stringify(fields));
    formData.append("image", image);
    axios
      .post(SAVE_ITEM, formData)
      .then((response) => {
        setTimeout(() => {
          navigate(URL_HOME);
        }, 3000);
        setErrorMessage(CONFIRM_ADD_ITEM);
      })
      .catch((error) => {
        setErrorMessage(ERROR_INVALID_ITEM);
      });
  }

  return (
    <div className="form-dark form-overflow">
      <form onSubmit={(e) => onCreatePost(e)}>
        <h2 className="mb-5">Ajouter un produit</h2>
        <div className="form-group my-3">
          <input
            value={fields.name}
            onChange={handleFieldChange}
            className="form-control"
            id="name"
            type="text"
            placeholder="Nom unique du produit"
            required
          />
        </div>
        <div className="form-group my-3">
          <textarea
            value={fields.description}
            onChange={handleFieldChange}
            id="description"
            className="form-control"
            type="text"
            name="description"
            placeholder="Description du produit"
            style={{ height: "6rem" }}
            required
          />
        </div>
        <div className="form-group my-3">
          <input
            value={fields.price}
            onChange={handleFieldChange}
            className="form-control"
            id="price"
            type="number"
            step="0.01"
            min="0"
            placeholder="Prix d'affichage"
            required
          />
        </div>
        <div className="form-group my-3">
          <select
            className="form-control"
            defaultValue={fields.category}
            onChange={handleFieldChange}
            id="category"
            placeholder="Catégorie"
            required
          >
            {CATEGORIES.map((category, key) => {
              return (
                <option className="text-dark" key={key} value={category.value}>
                  {category.name}
                </option>
              );
            })}
          </select>
        </div>
        <div className="form-group my-3">
          <input
            value={fields.quantity}
            onChange={handleFieldChange}
            className="form-control"
            id="quantity"
            type="number"
            min="0"
            placeholder="Quantité en Stock"
            required
          />
        </div>
        <div className="form-group my-3">
          <label htmlFor="image" className="btn btn-outline-light my-3">
            {image !== undefined ? "Changer l'image" : "Selectionner l'image"}
          </label>
          <input
            className="form-control"
            id="image"
            type="file"
            style={{ visibility: "hidden", padding: "0%", height: "0px" }}
            onChange={(e) => {
              setImage(e.target.files[0]);
            }}
            accept="image/*"
            required
          />
        </div>
        {showImagePreview()}
        <hr />
        {showErrorMessage()}
        <div className="form-group">
          <button className="btn btn-outline-light my-3" type="submit">
            Ajouter le produit
          </button>
        </div>
        <p className="forgot" onClick={() => navigate(URL_HOME)}>
          Voir la liste des produits
        </p>
      </form>
    </div>
  );
};
export default AddItemForm;

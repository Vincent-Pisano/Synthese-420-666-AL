import axios from "axios";
import { React, useState } from "react";
import { useNavigate } from "react-router";
import { useFormFields } from "../../services/FormFields";
import { GET_ITEM_IMAGE, SAVE_ITEM } from "../../utils/API";
import {
  CONFIRM_ADD_ITEM,
  CONFIRM_UPDATE_ITEM,
  ERROR_INVALID_ITEM,
  ERROR_SERVER_NOT_FOUND
} from "../../utils/MESSAGE";
import { CATEGORIES, ITEM_STATUS } from "../../utils/SECURITY";
import { URL_HOME } from "../../utils/URL";

const AddItemForm = ({ isModal, item, handleClose }) => {
  let navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");
  const [image, setImage] = useState(undefined);

  const [fields, handleFieldChange] = useFormFields(
    item !== undefined
      ? item
      : {
          name: "",
          description: "",
          price: undefined,
          quantity: undefined,
          category: "OTHER",
        }
  );

  function onCreatePost(e) {
    e.preventDefault();
    if (image !== undefined || item !== undefined) {
      if (
        item !== undefined &&
        JSON.stringify(item) === JSON.stringify(fields) && image === undefined
      ) {
        setErrorMessage("Aucune modification effectuée");
      } else {
        let formfields = fields;
        if (
          item !== undefined &&
          formfields.onSale &&
          (formfields.beforeSalePrice !== item.beforeSalePrice ||
            formfields.beforeSalePrice === 0)
        ) {
          formfields.beforeSalePrice = item.price;
        }
        addItem(formfields);
      }
    } else {
      setErrorMessage("Veuillez ajouter l'image du produit");
    }
  }

  function showErrorMessage() {
    if (errorMessage !== "") {
      return (
        <div
          className={
            errorMessage === CONFIRM_ADD_ITEM ||
            errorMessage === CONFIRM_UPDATE_ITEM
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
    } else if (item !== undefined) {
      return (
        <img
          style={{ height: "100%", width: "100%" }}
          alt="Produit"
          src={GET_ITEM_IMAGE + item.id}
        />
      );
    }
  }

  function addItem(formfields) {
    let formData = new FormData();

    formData.append("item", JSON.stringify(formfields));
    formData.append("image", image);
    axios
      .post(SAVE_ITEM, formData)
      .then((response) => {
        if (item !== undefined) {
          setTimeout(() => {
            handleClose();
          }, 3000);
          setErrorMessage(CONFIRM_UPDATE_ITEM);
        } else {
          setTimeout(() => {
            navigate(URL_HOME);
          }, 3000);
          setErrorMessage(CONFIRM_ADD_ITEM);
        }
      })
      .catch((error) => {
        if (error.response && error.response.status === 409) {
          setErrorMessage(ERROR_INVALID_ITEM);
        } else if (error.request) {
          setErrorMessage(ERROR_SERVER_NOT_FOUND);
        }
      });
  }

  function showRedirection() {
    if (item === undefined) {
      return (
        <p className="forgot" onClick={() => navigate(URL_HOME)}>
          Voir la liste des produits
        </p>
      );
    }
  }

  function showPromotion() {
    if (item !== undefined) {
      return (
        <>
          <div className="form-check my-3">
            <input
              defaultChecked={fields.onSale}
              onChange={handleFieldChange}
              className="form-check-input"
              name="onSale"
              id="onSale"
              type="checkbox"
            />
            <label className="form-check-label" htmlFor="onSale">
              {" "}
              Promotion{" "}
            </label>
          </div>
        </>
      );
    }
  }

  function showStatus() {
    if (item !== undefined) {
      return (
        <div className="form-group my-3">
          <select
            className="form-control"
            defaultValue={fields.status}
            onChange={handleFieldChange}
            id="status"
            placeholder="Catégorie"
            required
          >
            {ITEM_STATUS.map((status, key) => {
              return (
                <option className="text-dark" key={key} value={status.value}>
                  {status.name}
                </option>
              );
            })}
          </select>
        </div>
      );
    }
  }

  function showDeleteProduit() {
    if (item !== undefined) {
      return (
        <div className="form-group">
          <button
            className="btn btn-outline-danger my-3"
            type="button"
            onClick={() => deleteProduit()}
          >
            Supprimer le produit
          </button>
        </div>
      );
    }
  }

  function deleteProduit() {
    let formfields = fields;
        formfields.status = "DELETED";
        addItem(formfields);
  }

  return (
    <div
      className="form-dark form-overflow"
      style={isModal ? {} : { paddingBottom: "150px" }}
    >
      <form
        className={isModal ? "" : "form-dark-form"}
        onSubmit={(e) => onCreatePost(e)}
      >
        <h2 className="mb-5">
          {item !== undefined
            ? "Mettre à jour " + item.name
            : "Ajouter un produit"}
        </h2>
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
        {showPromotion()}
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
        {showStatus()}
        <div className="form-group my-3">
          <label htmlFor="image" className="btn btn-outline-light my-3">
            {image !== undefined || item !== undefined
              ? "Changer l'image"
              : "Selectionner l'image"}
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
            required={item === undefined}
          />
        </div>
        {showImagePreview()}

        <hr />
        {showErrorMessage()}
        <div className="d-flex justify-content-between">
          <div className="form-group">
            <button className="btn btn-outline-light my-3" type="submit">
              {item !== undefined
                ? "Modifier le produit"
                : "Ajouter le produit"}
            </button>
          </div>
          {showDeleteProduit()}
        </div>
        {showRedirection()}
      </form>
    </div>
  );
};
export default AddItemForm;

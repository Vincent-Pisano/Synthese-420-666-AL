import React from "react";
import Auth from "../../services/Auth";
import { GET_ITEM_IMAGE } from "../../utils/API";

const Item = ({ item, onClick }) => {
  function CheckIfNew() {
    var date = new Date();
    date.setDate(date.getDate() - 2);
    return new Date(item.creationDate) > date;
  }

  return (
    <div className="col mb-5" style={{minWidth: "18rem"}}>
      <div className="card h-100" style={{ minHeight: "100%" }}>
        {item.onSale ? (
          <div className="position-relative">
            <div className="badge bg-danger text-white position-absolute m-2">
              Promotion
            </div>
          </div>
        ) : (
          <></>
        )}
        {CheckIfNew() ? (
          <div className="position-relative">
            <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-dark">
              Nouveau
            </span>
          </div>
        ) : (
          <></>
        )}
        <img
          className="card-img-top" style={{height:"10rem"}}
          src={GET_ITEM_IMAGE + item.id}
          alt="..."
        />
        <div className="card-body p-4">
          <div className="text-center">
            <h5 className={!Auth.isAdministrator() ? "fw-bolder" : item.status === "HIDDEN" ? "fw-bolder text-danger" : "fw-bolder text-success"}>{item.name}</h5>
            {item.onSale ? (
              <>
                <span className="text-muted text-decoration-line-through">
                  ${item.beforeSalePrice.toFixed(2)}
                </span>
                -
              </>
            ) : (
              <></>
            )}
            ${item.price.toFixed(2)}
          </div>
        </div>
        <div className="card-footer p-4 pt-0 border-top-0 bg-transparent">
          <div className="text-center">
            <button className="btn btn-outline-dark mt-auto" onClick={() => onClick(item)}>
              Voir les options
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Item;

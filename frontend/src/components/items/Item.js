import React from "react";

const Item = ({ item }) => {
  return (
    <div className="col mb-5">
      <div className="card h-100">
        {item.isOnSale ? (
          <div className="badge bg-dark text-white position-absolute">Sale</div>
        ) : (
          <></>
        )}
        <img
          className="card-img-top"
          src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg"
          alt="..."
        />
        <div className="card-body p-4">
          <div className="text-center">
            <h5 className="fw-bolder">{item.name}</h5>
            {item.isOnSale ? (
              <>
                <span className="text-muted text-decoration-line-through">
                  ${item.oldPrice.toFixed(2)}
                </span>
                -
              </>
            ) : (
              <></>
            )}
            ${item.currentPrice.toFixed(2)}
          </div>
        </div>
        <div className="card-footer p-4 pt-0 border-top-0 bg-transparent">
          <div className="text-center">
            <a className="btn btn-outline-dark mt-auto" href="#">
              Voir les options
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Item;

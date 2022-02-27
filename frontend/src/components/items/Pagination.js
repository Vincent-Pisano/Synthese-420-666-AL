import React from "react";

const Pagination = ({ currentPage, setCurrentPage, items }) => {

  return (
    <nav aria-label="...">
          <ul className="pagination pagination-lg m-auto justify-content-center mb-3">
            <li
              className={currentPage === 0 ? "page-item disabled" : "page-item"}
            >
              <button
                className={currentPage === 0 ? "page-link text-danger" : "page-link text-dark"}
                onClick={() => setCurrentPage(currentPage - 1)}
              >
                Previous
              </button>
            </li>
            {items.map((items, i) => (
              <li className="page-item">
                <button
                className={currentPage === i ? "page-link bg-dark text-white" : "page-link text-dark "}
                onClick={() => setCurrentPage(i)}>
                  {i + 1}
                </button>
              </li>
            ))}

            <li
              className={
                currentPage === items.length - 1
                  ? "page-item disabled"
                  : "page-item"
              }
            >
              <button
                className={currentPage === items.length - 1 ? "page-link text-danger" : "page-link text-dark"}
                onClick={() => setCurrentPage(currentPage + 1)}
              >
                Next
              </button>
            </li>
          </ul>
        </nav>
  );
};
export default Pagination;

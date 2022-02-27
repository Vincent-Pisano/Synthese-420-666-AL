import { React, useEffect, useState } from "react";
import Item from "./Item";
import axios from "axios";
import { CATEGORIES } from "../../utils/SECURITY";
import CategoryDropdown from "./CategoryDropdown";
import { GET_ITEMS_FROM_CATEGORY } from "../../utils/API";
import { ERROR_NO_ITEM_FOUND_CATEGORY } from "../../utils/MESSAGE";
import Pagination from "./Pagination";

const ItemList = () => {
  const [category, setCategory] = useState(CATEGORIES[0]);
  const [items, setItems] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [errorMessage, setErrorMessage] = useState([]);

  useEffect(() => {
    axios
      .get(GET_ITEMS_FROM_CATEGORY + category.value)
      .then((response) => {
        var allItems = response.data;
        var itemsPaginated = [];
        var chunk = 8;
        for (var i = 0; i < Math.ceil(allItems.length / chunk); i++) {
          itemsPaginated[i] = {
            items: allItems.slice(i * chunk, i * chunk + chunk),
          };
        }
        setItems(itemsPaginated);
        setErrorMessage("");
      })
      .catch((error) => {
        setErrorMessage(ERROR_NO_ITEM_FOUND_CATEGORY);
        setItems([]);
      });
  }, [category.value]);

  function checkIfCategoryIsEmpty() {
    if (items.length > 0) {
      return (
        <>
          {items[currentPage].items.map((item, i) => (
            <Item key={i} item={item} />
          ))}
        </>
      );
    }
  }

  function showErrorMessage() {
    if (errorMessage !== "") {
      return (
        <div className="alert alert-danger w-50 m-auto" role="alert">
          {errorMessage}
        </div>
      );
    } else {
      return (
        <Pagination currentPage={currentPage} setCurrentPage={setCurrentPage} items={items} />
      );
    }
  }

  return (
    <section>
      <CategoryDropdown
        category={category}
        setCategory={setCategory}
        CATEGORIES={CATEGORIES}
      />
      <div className="container px-4 px-lg-5 mt-5 py-3">
        <div className="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
          {checkIfCategoryIsEmpty()}
        </div>
        {showErrorMessage()}
      </div>
    </section>
  );
};
export default ItemList;

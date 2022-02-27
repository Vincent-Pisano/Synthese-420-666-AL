import React from "react";

const CategoryDropdown = ({ category, setCategory, CATEGORIES }) => {
  return (
    <div className="bg-dark text-white w-100 pb-5" style={{marginTop: "-1%"}}>
      <div >
          <h2 className="text-center mb-3">Catégorie actuelle :</h2>
        
        <form className="m-auto" style={{ width: "250px" }}>
          <div className="form-group">
            <select
              className="form-control"
              defaultValue={category}
              onChange={(e) =>
                setCategory(
                  CATEGORIES.filter(
                    (category) => category.value === e.target.value
                  )[0]
                )
              }
              id="category"
              placeholder="Catégorie"
              required
            >
              {CATEGORIES.map((category, key) => {
                return (
                  <option
                    className="text-dark"
                    key={key}
                    value={category.value}
                  >
                    {category.name}
                  </option>
                );
              })}
            </select>
          </div>
        </form>
      </div>
    </div>
  );
};
export default CategoryDropdown;

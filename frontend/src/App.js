import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ROUTES } from "./utils/PATH";
import Navbar from "./components/navbar/Navbar";
import { ProtectedRoute } from "./services/ProtectedRoute";
import Home from "./components/Home";
import Footer from "./components/footer/Footer";
import CartContextProvider from "./contexts/CartContext";

function App() {
  return (
    <Router>
      <div
        className="App"
        style={{
          backgroundImage: `url(${
            process.env.PUBLIC_URL + "/img/background.jpg"
          })`,
        }}
      >
        <CartContextProvider>
          <Navbar />
          <div className="main">
            <Routes>
              {ROUTES.map((route, i) => (
                <Route
                  key={i}
                  exact
                  path={route.link}
                  element={<ProtectedRoute />}
                >
                  <Route
                    exact
                    path={route.link}
                    element={<route.component />}
                  />
                </Route>
              ))}
              <Route exact path="*" element={<Home />} />
            </Routes>
          </div>

          <Footer />
        </CartContextProvider>
      </div>
    </Router>
  );
}

export default App;

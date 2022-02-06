import './App.css';
import { BrowserRouter as Router, Routes, Route  } from "react-router-dom";
import { ROUTES } from "./utils/PATH";
import Navbar from "./components/navbar/Navbar";
import { ProtectedRoute } from "./services/ProtectedRoute";
import Home from './components/Home';
import Footer from './components/footer/Footer';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes> 
          {ROUTES.map((route, i) => (
            <Route key={i} exact path={route.link} element={<ProtectedRoute/>}>
              <Route exact path={route.link} element={<route.component/>}/>
            </Route>
          ))}
          <Route exact path="*" element={<Home/>}/>
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

export default App;

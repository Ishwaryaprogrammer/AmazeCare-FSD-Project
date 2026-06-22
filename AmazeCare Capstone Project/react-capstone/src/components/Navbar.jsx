import { Link } from "react-router-dom";
import logo from "../assets/logo.png";

const Navbar = () => {
  return (
    
    <nav className="navbar navbar-expand-lg bg-white shadow-sm py-3">
      <div className="container-fluid  px-4 px-lg-5">

        <Link to="/" className="navbar-brand d-flex align-items-center gap-2">
          <img src={logo} alt="Logo" width="50" height="50" />
          <span className="fw-bold text-primary fs-3">Amaze Care</span>
        </Link>

        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarContent">

          <ul className="navbar-nav mx-auto gap-lg-3">
            <li className="nav-item">
              <Link className="nav-link fw-semibold px-3" to="/">Home</Link>
            </li>

            <li className="nav-item">
              <Link className="nav-link fw-semibold px-3" to="/about">About Us</Link>
            </li>

            <li className="nav-item">
              <Link className="nav-link fw-semibold px-3" to="/service">Services</Link>
            </li>

            <li className="nav-item">
              <Link className="nav-link fw-semibold px-3" to="/contact">Contact Us</Link>
            </li>
          </ul>

          <div className="d-flex gap-2">
            <Link to="/login">
              <button className="btn btn-outline-primary squared-pill px-4">
                Login
              </button>
            </Link>

            <Link to="/register">
              <button className="btn btn-primary squared-pill px-4">
                Register
              </button>
            </Link>
          </div>

        </div>
      </div>
    </nav>
  );
};

export default Navbar;
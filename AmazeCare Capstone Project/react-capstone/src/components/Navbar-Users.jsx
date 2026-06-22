import { useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import "./Navbar-Users.css";

const NavbarUsers = () => {

    const navigate = useNavigate();

    const username = localStorage.getItem("username");

    const logout = () => {
        localStorage.clear();
        navigate("/login");
    };

    return (
        <nav className="navbar navbar-expand-lg user-navbar shadow">

            <div className="container-fluid">

                <div className="d-flex align-items-center">

                    <img
                        src={logo}
                        alt="logo"
                        width="40"
                        height="40"
                    />

                    <span className="navbar-brand ms-2 fw-bold">
                        Amaze Care
                    </span>

                </div>

                <div className="d-flex align-items-center">

                    <span className="navbar-text me-3">
                        Welcome {username}
                    </span>

                    <button
                        className="btn btn-logout"
                        onClick={logout}
                    >
                        Logout
                    </button>

                </div>

            </div>

        </nav>
    );
};

export default NavbarUsers;
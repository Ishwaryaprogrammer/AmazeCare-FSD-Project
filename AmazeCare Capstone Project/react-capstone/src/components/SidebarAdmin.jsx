import { Link } from "react-router-dom";
import "./Sidebar.css";

const SidebarAdmin = () => {

    return (
        <div className="patient-sidebar p-3">

            <ul className="nav flex-column">

                <li className="nav-item">
                    <Link
                        className="nav-link "
                        to="/admin"
                    >
                        <i className="bi bi-house me-2"></i>
                        Dashboard
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/admin/profile"
                    >
                        <i className="bi bi-person-circle me-2"></i>
                        Profile
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/admin/all-doctors"
                    >
                        <i className="bi bi-person-badge me-2"></i>
                        Manage Doctors
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/admin/onboard-doctor"
                    >
                        <i className="bi bi-calendar-check me-2"></i>
                        Onboard Doctor
                    </Link>
                </li>

            </ul>

        </div>
    );
};

export default SidebarAdmin;
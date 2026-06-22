import { Link } from "react-router-dom";
import "./Sidebar.css";

const SidebarDoctor = () => {

    return (
        <div className="patient-sidebar p-3">

            <ul className="nav flex-column">

                <li className="nav-item">
                    <Link
                        className="nav-link "
                        to="/doctor"
                    >
                        <i className="bi bi-house me-2"></i>
                        Dashboard
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/doctor/profile"
                    >
                        <i className="bi bi-person-circle me-2"></i>
                        Profile
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/doctor/availability"
                    >
                        <i className="bi bi-clock-history me-2"></i>
                        Availability
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/doctor/appointments"
                    >
                        <i className="bi bi-calendar-check me-2"></i>
                        Appointments
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/doctor/change-password"
                    >
                        <i className="bi bi-key me-2"></i>
                        Change Password
                    </Link>
                </li>

            </ul>

        </div>
    );
};

export default SidebarDoctor;
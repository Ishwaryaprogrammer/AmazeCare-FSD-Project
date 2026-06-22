import { Link } from "react-router-dom";
import "./Sidebar.css";

const SidebarPatient = () => {

    return (
        <div className="patient-sidebar p-3">



            <ul className="nav flex-column">

                <li className="nav-item">
                    <Link
                        className="nav-link "
                        to="/patient"
                    >
                        <i className="bi bi-house me-2"></i>
                        Dashboard
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/patient/profile"
                    >
                        <i className="bi bi-person-circle me-2"></i>
                        Profile
                    </Link>
                </li>

                
                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/patient/all-doctors"
                    >
                        <i className="bi bi-calendar-check me-2"></i>
                        All Doctors
                    </Link>
                </li>

            

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/patient/my-appointments"
                    >
                        <i className="bi bi-clipboard2-pulse me-2"></i>
                        My Appointments
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/patient/medical-records"
                    >
                        <i className="bi bi-file-earmark-medical me-2"></i>
                        Medical Records
                    </Link>
                </li>

                 <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/patient/medical-reports"
                    >
                        <i className="bi bi-file-earmark-text-fill me-2"></i>
                        Medical Reports
                    </Link>
                </li>

                <li className="nav-item">
                    <Link
                        className="nav-link"
                        to="/patient/change-password"
                    >
                        <i className="bi bi-key me-2"></i>
                        Change Password
                    </Link>
                </li>

            </ul>

        </div>
    );
};

export default SidebarPatient;
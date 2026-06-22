import NavbarUsers from "../components/Navbar-Users";
import SidebarPatient from "../components/SidebarPatient";
import "../components/Sidebar.css";
import { Outlet } from "react-router-dom";

const PatientDashboard = () => {

    return (
        <div>

            <NavbarUsers />

            <div className="d-flex">

                <SidebarPatient />

                <Outlet/>

            </div>

        </div>
    );
};

export default PatientDashboard;
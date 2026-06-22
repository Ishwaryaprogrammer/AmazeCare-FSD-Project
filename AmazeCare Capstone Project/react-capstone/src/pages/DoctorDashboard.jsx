
import NavbarUsers from "../components/Navbar-Users";
import SidebarDoctor from "../components/SidebarDoctor";
import "../components/Sidebar.css";
import { Outlet } from "react-router-dom";

const DoctorDashboard = () => {

    return (
        <div>

            <NavbarUsers />

            <div className="d-flex">

                <SidebarDoctor />
                <Outlet/>

            </div>

        </div>
    );
};

export default DoctorDashboard;
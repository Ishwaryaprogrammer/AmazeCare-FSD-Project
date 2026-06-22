import NavbarUsers from "../components/Navbar-Users";
import SidebarAdmin from "../components/SidebarAdmin";
import "../components/Sidebar.css";
import { Outlet } from "react-router-dom";


const AdminDashboard = () => {
    


    return (
        <div>

            <NavbarUsers />

            <div className="d-flex">
                <SidebarAdmin />
                <Outlet/>
            </div>

            



        </div>
    );
};

export default AdminDashboard;
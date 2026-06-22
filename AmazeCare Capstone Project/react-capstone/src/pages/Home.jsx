import { Outlet } from "react-router-dom"
import Navbar from "../components/NavBar"
import HomeWidget from "../components/public/HomeWidget"

const Home = () => {

    return (
        <div>
            <Navbar />
            <Outlet/>


        </div>


    )
}
export default Home
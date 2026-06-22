
import axios from "axios";
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom";
import ImageHome from "../assets/image-home.png"

const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const loginApi = "http://localhost:8080/api/auth/login";
    const getApi = "http://localhost:8080/api/auth/user-details";

    const navigate = useNavigate();
    const login = async (e) => {
        e.preventDefault()
        // console.log(username)
        // console.log(password)

        const config = {
            headers: {
                "Authorization": "Basic " + window.btoa(username + ":" + password)
            }
        }

        try {
            const response = await axios.get(loginApi, config)
            console.log(response.data)

            let token = response.data.token
            localStorage.setItem("token", token)
            localStorage.setItem("username", username)

            console.log(token)

            const config2 = {
                headers: {
                    "Authorization": "Bearer " + token
                }
            }

            const resp = await axios.get(getApi, config2)

            console.log(resp.data)

            const role = resp.data.role

            

            switch (role) {
                case "DOCTOR":
                    localStorage.setItem("isDoctor",true)
                    navigate("/doctor")
                    break;
                case "PATIENT":
                    navigate("/patient")
                    break;
                case "ADMIN":
                    localStorage.setItem("isAdmin",true)
                    navigate("/admin")
                    break;
                default:
                    break;
            }



        } catch (err) {
            setErrMsg("Invalid Credentials")
            console.log(err)
        }



    }




    return (
    <div className="container-fluid">
        <div className="row align-items-center justify-content-center" style={{ minHeight: "calc(100vh - 100px)" }}>
            
            <div className="col-lg-6 ">
                <img
                    src={ImageHome}
                    alt="Healthcare"
                    className="img-fluid"
                    style={{ maxWidth: "700px" }}
                />
            </div>

            <div className="col-lg-5">
                <form onSubmit={(e) => login(e)}>
                    <div className="card border-0 shadow-sm">
                        <div className="card-body p-4">
                            <h5 className="card-title h4 fw-bold text-center text-primary mb-4">Login Form</h5>

                            {errMsg && (<div className="alert alert-danger">
                                {errMsg}
                            </div>)}
                            
                            <div className="mb-3">
                                <label className="mb-2 fw-semibold"> Username </label>
                                <input type="text" className="form-control" required value={username}
                                    onChange={(e) => { 
                                        e.target.value !== "" ? setErrMsg("") : setErrMsg("Username is mandatory");
                                        (e.target.value).trim() !== "" ? setUsername(e.target.value) : setUsername("")
                                    }}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="mb-2 fw-semibold"> Password </label>
                                <input type="password" className="form-control" required value={password}
                                    onChange={(e) => {
                                        e.target.value !== "" ? setErrMsg("") : setErrMsg("Password is mandatory");
                                        (e.target.value).trim() !== "" ? setPassword(e.target.value) : setPassword("")
                                    }}
                                />
                            </div>
                            
                            <div className="text-center fw-semibold d-grid gap-2">
                                <button className="btn btn-primary rounded-pill mt-3" type="submit">
                                    Login
                                </button>
                                <div className="d-flex justify-content-center mt-3">
                                    <span>Don't have an account? </span>
                                    <Link className="text-primary text-decoration-none ms-2" to="/register">Register</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
)
}
export default Login 
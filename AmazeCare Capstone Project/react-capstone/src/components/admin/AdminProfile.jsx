import axios from "axios";
import { useEffect, useState } from "react";


const AdminProfile = () => {
    
    const [name, setName] = useState();
    const [email, setEmail] = useState();
    const getApi = "http://localhost:8080/api/auth/user-details"

    useEffect(() => {

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }

        const getDetails = async() => {

            try {

                const resp = await axios.get(getApi, config)
                setName(resp.data.username)
                setEmail(resp.data.email)
                


            } catch (err) {
                console.log(err.response.data)
            }


        }

        getDetails()




    }, [])

    return (
        

                <div
                    className="dashboard-content flex-grow-1 p-4"
                    style={{ minHeight: "calc(100vh - 56px)" }}
                >

                    <h2 className="fw-bold">
                        My Profile
                    </h2>


                    <div className="card shadow-sm border-0">

                        <div className="card-body p-4">

                            <div className="row mb-4">

                                <div className="col-md-4 fw-semibold">
                                    Username
                                </div>

                                <div className="col-md-8">
                                    {name}
                                </div>

                            </div>

                            <div className="row mb-4">

                                <div className="col-md-4 fw-semibold">
                                    Email
                                </div>

                                <div className="col-md-8">
                                    {email}
                                </div>

                            </div>

                            <div className="row">

                                <div className="col-md-4 fw-semibold">
                                    Role
                                </div>

                                <div className="col-md-8">
                                    ADMIN
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

    );
};

export default AdminProfile;
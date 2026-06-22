import { useEffect, useState } from "react";
import axios from "axios";

const PatientProfile = () => {

    const [name, setName] = useState()
    const [fullname, setFullName] = useState()
    const [email, setEmail] = useState()
    const [dob, setDob] = useState()
    const [gender, setGender] = useState()
    const [contact, setContact] = useState()
    const getApi = "http://localhost:8080/api/patient/view-profile"

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
                setFullName(resp.data.fullname)
                setEmail(resp.data.email)
                setDob(resp.data.dob)
                setGender(resp.data.gender)
                setContact(resp.data.contact)


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
                                    Full Name
                                </div>

                                <div className="col-md-8">
                                    {fullname}
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

                            <div className="row mb-4">

                                <div className="col-md-4 fw-semibold">
                                    Date of Birth
                                </div>

                                <div className="col-md-8">
                                    {String(dob).split("-").reverse().join("-")}
                                </div>

                            </div>

                            <div className="row mb-4">

                                <div className="col-md-4 fw-semibold">
                                    Gender
                                </div>

                                <div className="col-md-8">
                                    {gender}
                                </div>

                            </div>

                            <div className="row">

                                <div className="col-md-4 fw-semibold">
                                    Contact Number
                                </div>

                                <div className="col-md-8">
                                    {contact}
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

           
    );
};

export default PatientProfile;
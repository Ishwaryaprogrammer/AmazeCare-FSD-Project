import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const DoctorWidget=()=>{
    const statApi="http://localhost:8080/api/appointment/doctor/stat"
    const [label,setLabel]=useState([]);
    const [count, setCount]=useState([]);

    useEffect(()=>{
        
        const getStats = async() => {
            const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }

            try {

                const resp = await axios.get(statApi, config)
                console.log(resp)
                setLabel(resp.data.label)
                setCount(resp.data.count)
                


            } catch (err) {
                console.log(JSON.stringify(err))
            }


        }

        getStats()

    },[])

return(
    <div>
        <div className="dashboard-content flex-grow-1 p-4">

                    <div className="mb-4">

                        <h2 className="fw-bold">
                            Welcome Doctor 🩺
                        </h2>


                    </div>

                    {/* Statistics */}

                    <div className="row g-4 mb-5 text-center">

                        <div className="col-md-4">

                            <div className="card bg-primary text-white border-0 shadow">

                                <div className="card-body">

                                    <h6>
                                        <i className="bi bi-calendar-event me-2"></i>
                                        {label.length>0?label[0]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                        {count.length>0?count[0]:""}
                                    </h2>

                                </div>

                            </div>

                        </div>

                        <div className="col-md-4">

                            <div className="card bg-success text-white border-0 shadow">

                                <div className="card-body">

                                    <h6>
                                        <i className="bi bi-check-circle me-2"></i>
                                        {label.length>1?label[1]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                         {count.length>1?count[1]:""}
                                    </h2>

                                </div>

                            </div>

                        </div>

                       

                        <div className="col-md-4">

                            <div className="card bg-danger text-white border-0 shadow">

                                <div className="card-body">

                                    <h6>
                                        <i className="bi bi-hourglass-split me-2"></i>
                                        {label.length>2?label[2]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                         {count.length>2?count[2]:""}
                                    </h2>

                                </div>

                            </div>

                        </div>

                    </div>
                    <div className="row g-4">

                        <div className="col-lg-3 col-md-6">

                            <Link
                                to="/doctor/availability"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-clock-history fs-1 text-primary"></i>

                                        <h5 className="mt-3 text-dark">
                                            Availability
                                        </h5>

                                        <p className="text-muted">
                                            Manage your consultation timings.
                                        </p>

                                        <button className="btn btn-primary">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                        <div className="col-lg-3 col-md-6">

                            <Link
                                to="/doctor/appointments"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-calendar-check fs-1 text-success"></i>

                                        <h5 className="mt-3 text-dark">
                                            Appointments
                                        </h5>

                                        <p className="text-muted">
                                            View and manage appointments.
                                        </p>

                                        <button className="btn btn-success">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                        <div className="col-lg-3 col-md-6">

                            <Link
                                to="/doctor/profile"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-person-circle fs-1 text-info"></i>

                                        <h5 className="mt-3 text-dark">
                                            Profile
                                        </h5>

                                        <p className="text-muted">
                                            Update professional information.
                                        </p>

                                        <button className="btn btn-info text-white">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                        <div className="col-lg-3 col-md-6">

                            <Link
                                to="/doctor/change-password"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-shield-check fs-1 text-danger"></i>

                                        <h5 className="mt-3 text-dark">
                                            Change Password
                                        </h5>

                                        <p className="text-muted">
                                            Update the temporary password
                                        </p>

                                        <button className="btn btn-danger">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                    </div>

                </div>
    </div>

)
}
export default DoctorWidget
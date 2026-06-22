
import axios from "axios";
import { useEffect, useState } from "react";
import {Link} from "react-router-dom";
const PatientWidget=()=>{

     const statApi="http://localhost:8080/api/appointment/patient/stat"
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
                console.log(JSON.stringify.err)
            }


        }

        getStats()

    },[])

return(
    <div>
        <div className="dashboard-content flex-grow-1 p-4">

                    <div className="mb-4">

                        <h2 className="fw-bold">
                            Welcome Back 👋
                        </h2>


                    </div>

                    <div className="row g-4 mb-5 text-center">

                        <div className="col-md-3">
                            <div className="card bg-primary text-white border-0 shadow">
                                <div className="card-body">
                                    <h6>
                                        <i className="bi bi-calendar-check me-2 "></i>
                                         {label.length>0?label[0]:""}
                                    </h6>

                                    <h2 className="fw-bold text-center">{count.length>0?count[0]:""}</h2>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-3">
                            <div className="card bg-success text-white border-0 shadow">
                                <div className="card-body">
                                    <h6>
                                        <i className="bi bi-file-earmark-medical me-2 text-center"></i>
                                         {label.length>1?label[1]:""}
                                    </h6>

                                    <h2 className="fw-bold text-center">{count.length>1?count[1]:""}</h2>
                                </div>
                            </div>
                        </div>

                        <div className="col-md-3">
                            <div className="card bg-warning text-dark border-0 shadow">
                                <div className="card-body">
                                    <h6>
                                        <i className="bi bi-check-circle me-2 text-center"></i>
                                         {label.length>2?label[2]:""}
                                    </h6>

                                    <h2 className="fw-bold text-center">{count.length>2?count[2]:""}</h2>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="card bg-secondary text-white border-0 shadow">
                                <div className="card-body">
                                    <h6>
                                        <i className="bi bi-check-circle me-2 text-center"></i>
                                         {label.length>3?label[3]:""}
                                    </h6>

                                    <h2 className="fw-bold text-center">{count.length>3?count[3]:""}</h2>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div className="row g-4">

                        <div className="col-lg-3 col-md-6">
                            <Link
                                to="/patient//all-doctors"
                                className="text-decoration-none"
                            >
                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-calendar-plus fs-1 text-primary"></i>

                                        <h5 className="mt-3 text-dark">
                                            Book Appointment
                                        </h5>

                                        <p className="text-muted">
                                            Schedule appointments with available healthcare providers.
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
                                to="/patient/my-appointments"
                                className="text-decoration-none"
                            >
                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-clipboard2-pulse fs-1 text-success"></i>

                                        <h5 className="mt-3 text-dark">
                                            My Appointments
                                        </h5>

                                        <p className="text-muted">
                                            View upcoming, completed and cancelled appointments.
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
                                to="/patient/medical-records"
                                className="text-decoration-none"
                            >
                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-file-earmark-medical fs-1 text-danger"></i>

                                        <h5 className="mt-3 text-dark">
                                            Medical Records
                                        </h5>

                                        <p className="text-muted">
                                            Access reports, prescriptions and healthcare documents.
                                        </p>

                                        <button className="btn btn-danger">
                                            Open
                                        </button>

                                    </div>

                                </div>
                            </Link>
                        </div>

                        <div className="col-lg-3 col-md-6">
                            <Link
                                to="/patient/medical-reports"
                                className="text-decoration-none"
                            >
                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-file-earmark-text-fill fs-1 text-info"></i>

                                        <h5 className="mt-3 text-dark">
                                            Medical Reports
                                        </h5>

                                        <p className="text-muted">
                                            Manage your medical reports
                                        </p>

                                        <button className="btn btn-info text-white">
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
export default PatientWidget
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import AdminGraph1 from "./AdminGraph1";
import AdminGraph2 from "./AdminGraph2";

const AdminWidget=()=>{

    const apiUrl="http://localhost:8080/api/admin/stat"
    const [label,setLabel]=useState([]);
    const [count,setCount]=useState([]);


    useEffect(()=>{
        const config={
            headers:{
                "Authorization":"Bearer "+localStorage.getItem("token")
            }
            
        }

        const getStats=async()=>{
            try{
                const statRes=await axios.get(apiUrl,config)
                setLabel(statRes.data.label)
                setCount(statRes.data.count)
                
            }catch(err){
                console.log(err?.response)
            }
            

        }

        getStats()



    },[])
return(
<div>
<div className="dashboard-content flex-grow-1 p-4">

                    <div className="mb-4">

                        <h2 className="fw-bold">
                            Welcome Admin 👋
                        </h2>

                    

                    </div>

                    {/* Statistics */}

                    <div className="row g-4 mb-5 text-center">

                        <div className="col-md-3">

                            <div className="card bg-primary text-white border-0 shadow">

                                <div className="card-body mb-3">

                                    <h6>
                                        <i className="bi bi-people me-2"></i>
                                        {label.length>0?label[0]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                        {count.length>0?count[0]:0}
                                    </h2>

                                </div>

                            </div>

                        </div>

                        <div className="col-md-3">

                            <div className="card bg-success text-white border-0 shadow">

                                <div className="card-body mb-3">

                                    <h6>
                                        <i className="bi bi-person-badge me-2"></i>
                                       {label.length>1?label[1]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                        {count.length>1?count[1]:0}
                                    </h2>

                                </div>

                            </div>

                        </div>

                        <div className="col-md-3">

                            <div className="card bg-warning text-dark border-0 shadow">

                                <div className="card-body mb-3">

                                    <h6>
                                        <i className="bi bi-check-circle me-2"></i>
                                        {label.length>2?label[2]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                        {count.length>2?count[2]:0}
                                    </h2>

                                </div>

                            </div>

                        </div>

                        <div className="col-md-3">

                            <div className="card bg-danger text-white border-0 shadow">

                                <div className="card-body mb-3">

                                    <h6>
                                        <i className="bi bi-hourglass-split me-2"></i>
                                        {label.length>3?label[3]:""}
                                    </h6>

                                    <h2 className="fw-bold">
                                        {count.length>3?count[3]:0}
                                    </h2>

                                </div>

                            </div>

                        </div>

                    </div>

                    {/* Charts */}

                    <div className="row g-4 mb-5">

                        <AdminGraph1 totalDoctors={count[2]}/>
                        <AdminGraph2/>

                    </div>

                    {/* Quick Access */}

                  
                    <div className="row g-4">

                        <div className="col-lg-3">

                            <Link
                                to="/admin/all-doctors"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-calendar-check fs-1 text-primary"></i>

                                        <h5 className="mt-3 text-dark">
                                            Manage Appointments
                                        </h5>

                                        <p className="text-muted">
                                            Approve, reject and monitor appointments.
                                        </p>

                                        <button className="btn btn-primary">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                        <div className="col-lg-3">

                            <Link
                                to="/admin/onboard-doctor"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-person-plus fs-1 text-success"></i>

                                        <h5 className="mt-3 text-dark">
                                            Onboard Doctor
                                        </h5>

                                        <p className="text-muted">
                                            Add new doctors to the platform.
                                        </p>

                                        <button className="btn btn-success">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                        <div className="col-lg-3">

                            <Link
                                to="/admin/all-doctors"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-person-badge fs-1 text-warning"></i>

                                        <h5 className="mt-3 text-dark">
                                            Manage Doctors
                                        </h5>

                                        <p className="text-muted">
                                            Update and manage doctor profiles.
                                        </p>

                                        <button className="btn btn-warning">
                                            Open
                                        </button>

                                    </div>

                                </div>

                            </Link>

                        </div>

                        <div className="col-lg-3">

                            <Link
                                to="/admin/analytics"
                                className="text-decoration-none"
                            >

                                <div className="card quick-card border-0 shadow-sm h-100">

                                    <div className="card-body text-center">

                                        <i className="bi bi-bar-chart fs-1 text-danger"></i>

                                        <h5 className="mt-3 text-dark">
                                            Appointment Analytics
                                        </h5>

                                        <p className="text-muted">
                                            Analyze appointments and trends.
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
export default AdminWidget
import { useEffect, useState } from "react";
import NavbarUsers from "../Navbar-Users";
import SidebarDoctor from "../SidebarDoctor";
import axios from "axios";

const DoctorProfile = () => {

    const [name,setName]=useState("")
    const [fullname,setFullName]=useState("")
    const [email,setEmail]=useState("")
    const [specialty,setSpecialty]=useState("")
    const [experience, setExperience]=useState("")
    const [qualification, setQualification]=useState("")
    const [designation, setDesignation]=useState("")
    const getApi="http://localhost:8080/api/doctor/view-profile"

    

    useEffect(()=>{
        const config={
        headers:{
            "Authorization":"Bearer "+localStorage.getItem("token")
        }
    }

        const getDetails= async()=>{
            

            try{
                const resp =await axios.get(getApi,config)
                console.log(resp.data)
                setName(resp.data.username)
                setFullName(resp.data.fullname)
                setEmail(resp.data.email)
                setSpecialty(resp.data.specialty)
                setExperience(resp.data.experience)
                setQualification(resp.data.qualification)
                setDesignation(resp.data.designation)

            }catch(err){
                console.log(JSON.stringify(err))
            }

        }

        getDetails();

    },[])




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
                                    User Name
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
                                    Specialty
                                </div>
                                <div className="col-md-8">
                                    {specialty}
                                </div>
                            </div>

                            <div className="row mb-4">
                                <div className="col-md-4 fw-semibold">
                                    Experience
                                </div>
                                <div className="col-md-8">
                                    {experience} Years
                                </div>
                            </div>

                            <div className="row mb-4">
                                <div className="col-md-4 fw-semibold">
                                    Qualification
                                </div>
                                <div className="col-md-8">
                                    {qualification}
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-4 fw-semibold">
                                    Designation
                                </div>
                                <div className="col-md-8">
                                    {designation}
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

          
    );
};

export default DoctorProfile;
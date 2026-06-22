import axios from "axios";
import { useEffect, useState } from "react"
import { useParams } from "react-router-dom";

const ViewConsultation = () => {
    const [consultation,setConsultation]=useState([])
    const {appid} =useParams();

    useEffect(()=>{
        const getApi=`http://localhost:8080/api/consultation/get-one/${appid}`
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        };
        const getDetails=async()=>{
            try{
                const response=await axios.get(getApi,config)
                console.log(response.data)
                setConsultation(response.data)

            }catch(err){
                console.log(err.response.data)
            }
        }
        getDetails()
    },[])

    return (
        <div  className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)" }}>

            {/* Consultation Details Card */}

             <div className="card border-0 shadow-lg mb-4 rounded-4">
                

                <div className="card-body">

                        <div className="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">

                        <h3 className="fw-bold mb-0">
                            Consultation Details
                        </h3>


                    </div>

                    {/* Doctor Card */}

                    <div className="card border-0 shadow-sm mb-4 rounded-4">

                        <div className="card-body">

                            <div className="row align-items-center">

                                <div className="col-md-3">

                                    <div className="d-flex align-items-center">

                                        

                                        <div>

                                            <h5 className="fw-bold mb-1">
                                                Dr. {consultation.doctorName}
                                            </h5>

                                            <div className="text-muted">
                                                {consultation.doctorSpecialty}
                                            </div>

                                            <small className="text-secondary">
                                                {consultation.doctorDesignation}
                                            </small>

                                        </div>

                                    </div>

                                </div>

                                <div className="col-md-3">

                                    <div className="fw-semibold">
                                        Qualification
                                    </div>

                                    <div>
                                        {consultation.doctorQualification}
                                    </div>

                                </div>

                                <div className="col-md-3">

                                    <div className="fw-semibold">
                                        Experience
                                    </div>

                                    <div>
                                        {consultation.doctorExperience} Years
                                    </div>

                                </div>

                                 <div className="col-md-3">
                                    <i className="bi bi-calendar-event-fill text-primary fs-2 me-3"></i>

                                    <div className="text-muted small">
                                        Consultation Date
                                    </div>

                                    <div className="fw-semibold">
                                        {String(consultation.date).split("-").reverse().join("-")}
                                    </div>

                                </div>

                                

                            </div>

                        </div>

                    </div>

                    {/* Patient Card */}

                    <div className="card border-0 shadow-sm mb-4 rounded-4">

                        <div className="card-header bg-primary text-white  fw-semibold">
                            Patient Information
                        </div>

                        <div className="card-body">

                            <div className="row">

                                <div className="col-md-3">

                                    <div className="text-muted small">
                                        Name
                                    </div>

                                    <div className="fw-bold">
                                        {consultation.patientName}
                                    </div>

                                </div>

                                <div className="col-md-3">

                                    <div className="text-muted small">
                                        Contact
                                    </div>

                                    <div className="fw-semibold">
                                        {consultation.patientContact}
                                    </div>

                                </div>

                                <div className="col-md-3">

                                    <div className="text-muted small">
                                        Gender
                                    </div>

                                    <div className="fw-semibold">
                                        {consultation.patientGender}
                                    </div>

                                </div>

                                <div className="col-md-3">

                                    <div className="text-muted small">
                                        Date of Birth
                                    </div>

                                    <div className="fw-semibold">
                                        {String(consultation.patientDob).split("-").reverse().join("-")}
                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                  

                    {/* Consultation Table */}

                    <div className="table-responsive">

                            <table className="table table-hover table-bordered align-middle mb-0">

                            <tbody>

                                <tr>

                                    <th
                                        className="bg-light"
                                        style={{ width: "250px" }}
                                    >
                                        Symptoms
                                    </th>

                                    <td>
                                        {consultation.symptoms}
                                    </td>

                                </tr>

                                <tr>

                                    <th className="bg-light">
                                        Physical Examination
                                    </th>

                                    <td>
                                        {consultation.phyExam}
                                    </td>

                                </tr>

                                <tr>

                                    <th className="bg-light">
                                        Treatment
                                    </th>

                                    <td>
                                        {consultation.treatment}
                                    </td>

                                </tr>

                                <tr>

                                    <th className="bg-light">
                                        Recommendations
                                    </th>

                                    <td>
                                        {consultation.recommended}
                                    </td>

                                </tr>

                                <tr>

                                    <th className="bg-light">
                                        Prescription
                                    </th>

                                    <td>
                                        {consultation.prescription}
                                    </td>

                                </tr>

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>

        </div>
    )
}
export default ViewConsultation
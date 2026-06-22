import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const MedicalRecords = () => {

    const navigate = useNavigate()

    const [specialtyList, setSpecialtyList] = useState([])
    const [specialty, setSpecialty] = useState("");
    const [patientName, setPatientName] = useState("")
    const [patientContact, setPatientContact] = useState("")
    const [patientGender, setPatientGender] = useState("")
    const [patientDob, setPatientDob] = useState("")
    const [consultations, setConsultations] = useState([]);
    const [totalElements, setTotalElements] = useState(0)
    const [totalPages, setTotalPages] = useState(0)
    const [arry, setArry] = useState([])
    const [currentPage, setCurrentPage] = useState(0)
    const [size, setSize] = useState(10)
    const { appid } = useParams()

    const isDoctor = localStorage.getItem("isDoctor")
    const getApi = isDoctor ? `http://localhost:8080/api/consultation/all/${appid}?page=${currentPage}&size=${size}`
        : `http://localhost:8080/api/consultation/all?page=${currentPage}&size=${size}`

    const getApiWithStatus = isDoctor ? `http://localhost:8080/api/consultation/all-by-status/${appid}?page=${currentPage}&size=${size}`
        : `http://localhost:8080/api/consultation/all-by-status?page=${currentPage}&size=${size}`

    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        const getSpecialties = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/doctor/get-specialties")
                setSpecialtyList(response.data.specialtyList)
            } catch (err) {
                console.log(err?.response?.data)
            }
        }

        if (specialtyList.length === 0) {
            getSpecialties()
        }

        const getDetails = async () => {
            try {
                if (specialty === "") {
                    const response = await axios.get(getApi, config)
                    setTotalElements(response.data.totalElements)
                    setConsultations(response.data.list)
                    
                    if(response.data.list.length > 0) {
                        setPatientName(response.data.list[0].patientName)
                        setPatientGender(response.data.list[0].patientGender)
                        setPatientContact(response.data.list[0].patientContact)
                        setPatientDob(response.data.list[0].patientDob)
                    }
                    
                    setTotalPages(response.data.totalPages)
                    setArry(Array.from({ length: response.data.totalPages }))
                } else {
                    const response = await axios.get(getApiWithStatus, {
                        headers: {
                            "Authorization": "Bearer " + localStorage.getItem("token")
                        },
                        params: {
                            specialty: specialty
                        }
                    })
                    setTotalElements(response.data.totalElements)
                    setConsultations(response.data.list)

                    if(response.data.list.length > 0) {
                        setPatientName(response.data.list[0].patientName)
                        setPatientGender(response.data.list[0].patientGender)
                        setPatientContact(response.data.list[0].patientContact)
                        setPatientDob(response.data.list[0].patientDob)
                    }

                    setTotalPages(response.data.totalPages)
                    setArry(Array.from({ length: response.data.totalPages }))
                }

            } catch (err) {
                console.log(err?.response?.data)
            }

        }
        getDetails()

    }, [specialty, currentPage])

    const getAge=(date)=>{
        const dob = new Date(date);
  const today = new Date();

  let age = today.getFullYear() - dob.getFullYear();

  const monthDiff = today.getMonth() - dob.getMonth();
  const dayDiff = today.getDate() - dob.getDate();

  if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
    age--;
  }

  return age;
    }

  

    return (
        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >
            <button
                type="button"
                className="btn btn-outline-secondary px-4 mb-4"
                onClick={() => {
                    navigate(-1)
                }}
            >
                <i className="bi bi-arrow-left me-2"></i>
                Back
            </button>

            <h2 className="fw-bold mb-4 text-center">
                Medical Records
            </h2>

            <div className="card shadow-sm border-0 mb-4">
                <div className="card-body">
                    <h5 className="fw-bold text-primary mb-3">
                        Patient Information
                    </h5>
                    <div className="row g-3">
                        <div className="col-md-3">
                            <span className="text-muted small d-block">Name</span>
                            <strong className="text-dark">{patientName || "—"}</strong>
                        </div>
                        <div className="col-md-3">
                            <span className="text-muted small d-block">Contact</span>
                            <strong className="text-dark">{patientContact || "—"}</strong>
                        </div>
                        <div className="col-md-2">
                            <span className="text-muted small d-block">Gender</span>
                            <strong className="text-dark">{patientGender || "—"}</strong>
                        </div>
                        <div className="col-md-2">
                            <span className="text-muted small d-block">Date of Birth</span>
                            <strong className="text-dark">{String(patientDob).split("-").reverse().join("-") || "—"}</strong>
                        </div>
                        <div className="col-md-2">
                            <span className="text-muted small d-block">Age</span>
                            <strong className="text-dark">{getAge(patientDob)} Years</strong>
                        </div>
                    </div>
                </div>
            </div>

            <div className="card shadow-sm border-0">
                <div className="card-body">
                    <div className="d-flex flex-wrap justify-content-between align-items-center mb-4">
                        <h5 className="fw-bold mb-0">
                            Consultation History
                        </h5>

                        <select
                            className="form-select fw-semibold"
                            style={{ maxWidth: "250px", width: "100%" }}
                            value={specialty}
                            onChange={(e) => {
                                setSpecialty(e.target.value);
                                setCurrentPage(0);
                            }}
                        >
                            <option value="">All Specialties</option>
                            {specialtyList.map((specialty, index) => (
                                <option key={index} value={specialty}>
                                    {specialty}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">
                            <thead className="table-primary">
                                <tr>
                                    <th>Date</th>
                                    <th>Doctor Details</th>
                                    <th>Doctor Specialty</th>
                                    <th>Symptoms</th>
                                    <th>Treatment</th>
                                    <th>Prescription</th>
                                </tr>
                            </thead>
                            <tbody>
                                {consultations.map((consultation, index) => (
                                    <tr key={index}>
                                        <td>{String(consultation.date).split("-").reverse().join("-")}</td>
                                        <td>
                                            <div>
                                                <div className="fw-bold text-primary">
                                                    Dr. {consultation.doctorName}
                                                </div>
                                                <small className="d-block">
                                                    {consultation.doctorDesignation}
                                                </small>
                                                <small className="d-block">
                                                    {consultation.doctorExperience} Years Experience
                                                </small>
                                                <small className="d-block text-secondary">
                                                    {consultation.doctorQualification}
                                                </small>
                                            </div>
                                        </td>
                                        <td>{consultation.doctorSpecialty}</td>
                                        <td>{consultation.symptoms}</td>
                                        <td>{consultation.treatment}</td>
                                        <td>{consultation.prescription}</td>
                                        {/* Removed the extra empty <td> that was breaking your layout */}
                                    </tr>
                                ))}
                                {consultations.length === 0 && (
                                    <tr>
                                        <td colSpan="6" className="text-center py-4 text-muted">
                                            No consultations found.
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    {/* Pagination */}
                    <div className="d-flex flex-wrap justify-content-between align-items-center mt-4 border-top pt-3 gap-3">
                        <div className="fw-semibold text-secondary">
                            Total Records: {totalElements}
                        </div>

                        <div className="d-flex align-items-center gap-1">
                            <button
                                className="btn btn-outline-primary rounded-pill" 
                                disabled={currentPage === 0} 
                                onClick={() => { setCurrentPage(currentPage - 1) }}
                            >
                                <i className="bi bi-chevron-left"></i>
                                <span className="ms-1 d-none d-sm-inline">Previous</span>
                            </button>

                            {arry.map((_, index) => (
                                <button 
                                    key={index}
                                    className={`btn rounded-pill ${currentPage === index ? 'btn-primary' : 'btn-outline-primary'}`}
                                    onClick={() => { setCurrentPage(index) }}
                                >
                                    {index + 1}
                                </button>
                            ))}

                            <button
                                className="btn btn-outline-primary rounded-pill" 
                                disabled={totalPages === 0 || currentPage === totalPages - 1} 
                                onClick={() => { setCurrentPage(currentPage + 1) }}
                            >
                                <span className="me-1 d-none d-sm-inline">Next</span>
                                <i className="bi bi-chevron-right"></i>
                            </button>
                        </div>

                        <div className="bg-primary text-white px-3 py-2 rounded-pill fw-bold shadow-sm">
                            Page {totalPages > 0 ? currentPage + 1 : 0} of {totalPages}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MedicalRecords;
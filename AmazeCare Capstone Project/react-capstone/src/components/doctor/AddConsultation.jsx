import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

const AddConsultation = () => {

    const navigate = useNavigate()
    const { appid } = useParams()

    // Form States
    const [symptoms, setSymptoms] = useState("")
    const [phyExam, setPhyExam] = useState("")
    const [treatment, setTreatment] = useState("")
    const [recommended, setRecommended] = useState("")
    const [prescription, setPrescription] = useState("")
    const [msg, setMsg] = useState("")

    // New State for Patient Details
    const [patientDetails, setPatientDetails] = useState(null)

    const postApi = `http://localhost:8080/api/consultation/add/${appid}`
    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        const getPatientDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/appointment/doctor/patient-details/${appid}`, config);
                setPatientDetails(response.data)
            } catch (err) {
                console.log(err)
            }
        };
        getPatientDetails()
    }, [appid])

    const Add = async (e) => {
        e.preventDefault();
        const body = {
            symptoms: symptoms,
            phyExam: phyExam,
            treatment: treatment,
            recommended: recommended,
            prescription: prescription
        }

        try {
            const response = await axios.post(postApi, body, config)
            setMsg("Consultation Completed Successfully")
            setSymptoms("")
            setPhyExam("")
            setTreatment("")
            setRecommended("")
            setPrescription("")
            setTimeout(() => {
                navigate(-1)
            }, 2000);

        } catch (err) {
            console.log(err?.response?.data)
            setMsg("")
        }
    }
    const getAge = (date) => {
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
        <div className="container py-4">

            <div
                className="card border-0 shadow-sm mx-auto"
                style={{ maxWidth: "1100px" }}
            >

                <div className="card-body p-4">

                    {msg &&
                        <div className="toast show align-items-center text-white bg-success border-0 w-100 mb-3" role="alert" aria-live="assertive" aria-atomic="true">
                            <div className="d-flex">
                                <div className="toast-body text-center w-100">
                                    {msg}
                                </div>
                                <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setMsg(""); navigate(-1) }}></button>
                            </div>
                        </div>
                    }

                    <div className="d-flex justify-content-between align-items-center mb-4">

                        <h3 className="fw-bold mb-0">
                            Add Consultation
                        </h3>

                        <div className="btn-group-rounded">
                            <button className="btn btn-success me-2"
                                onClick={() => {
                                    navigate(`/doctor/medical-records/${appid}`)
                                }}
                            >
                                <i className="bi bi-clipboard2-pulse-fill me-2"></i>
                                Medical Records
                            </button>

                            <button className="btn btn-primary"
                                onClick={() => {
                                    navigate(`/doctor/medical-reports/${appid}`)
                                }}>
                                <i className="bi bi-file-earmark-medical-fill me-2"></i>
                                Medical Reports
                            </button>
                        </div>
                    </div>

                    {/* New Patient Details Card */}
                    {patientDetails && (
                        <div className="card border-0 mb-4 rounded-3">
                            <div className="card-body">
                                <h5 className="fw-bold text-primary mb-3">
                                    <i className="bi bi-person-lines-fill me-2"></i>
                                    Patient Information
                                </h5>
                                <div className="row g-3">
                                    <div className="col-md-3">
                                        <span className="text-muted d-block small">Full Name</span>
                                        <span className="fw-semibold">{patientDetails.fullname}</span>
                                    </div>
                                    <div className="col-md-3">
                                        <span className="text-muted d-block small">Date of Birth</span>
                                        <span className="fw-semibold">
                                            {patientDetails.dob ? String(patientDetails.dob).split("-").reverse().join("-") : "N/A"}
                                        </span>
                                    </div>
                                    <div className="col-md-2">
                                        <span className="text-muted d-block small">Age</span>
                                        <span className="fw-semibold">{getAge(patientDetails.dob)} Years</span>
                                    </div>
                                    <div className="col-md-2">
                                        <span className="text-muted d-block small">Gender</span>
                                        <span className="fw-semibold">{patientDetails.gender}</span>
                                    </div>
                                    <div className="col-md-2">
                                        <span className="text-muted d-block small">Contact</span>
                                        <span className="fw-semibold">{patientDetails.contact}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}

                    <form onSubmit={(e) => { Add(e) }}>

                        <div className="mb-3">
                            <label className="form-label fw-semibold">
                                Symptoms <span className="text-danger">*</span>
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                name="symptoms"
                                value={symptoms}
                                onChange={(e) => { (e.target.value).trim() !== "" ? setSymptoms(e.target.value) : setSymptoms("") }}
                                placeholder="Enter patient symptoms"
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label fw-semibold">
                                Physical Examination <span className="text-danger">*</span>
                            </label>
                            <textarea
                                className="form-control"
                                rows="3"
                                name="phyExam"
                                value={phyExam}
                                onChange={(e) => { (e.target.value).trim() !== "" ? setPhyExam(e.target.value) : setPhyExam("") }}
                                placeholder="Enter physical examination details"
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label fw-semibold">
                                Treatment <span className="text-danger">*</span>
                            </label>
                            <textarea
                                className="form-control"
                                rows="3"
                                name="treatment"
                                value={treatment}
                                onChange={(e) => { (e.target.value).trim() !== "" ? setTreatment(e.target.value) : setTreatment("") }}
                                placeholder="Enter treatment details"
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label fw-semibold">
                                Recommended <span className="text-danger">*</span>
                            </label>
                            <textarea
                                className="form-control"
                                rows="3"
                                name="recommended"
                                value={recommended}
                                onChange={(e) => { (e.target.value).trim() !== "" ? setRecommended(e.target.value) : setRecommended("") }}
                                placeholder="Enter recommendations"
                                required
                            />
                        </div>

                        <div className="mb-4">
                            <label className="form-label fw-semibold">
                                Prescription <span className="text-danger">*</span>
                            </label>
                            <textarea
                                className="form-control"
                                rows="3"
                                name="prescription"
                                value={prescription}
                                onChange={(e) => { (e.target.value).trim() !== "" ? setPrescription(e.target.value) : setPrescription("") }}
                                placeholder="Enter prescription details eg. Paracetamol with 0-0-1 After Food"
                                required
                            />
                        </div>

                        <div className="d-flex justify-content-between">
                            <button
                                type="button"
                                className="btn btn-outline-secondary px-4"
                                onClick={() => {
                                    navigate(-1)
                                }}
                            >
                                Back
                            </button>

                            <button
                                type="submit"
                                className="btn btn-primary px-5"
                            >
                                Save Consultation
                            </button>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    );
};

export default AddConsultation;
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const ManageDoctor = () => {

    const navigate = useNavigate();
    const [specialtyList, setSpecialtyList] = useState([])
    const [word, setWord] = useState("")

    const [doctors, setDoctors] = useState([]);

    const [specialty, setSpecialty] = useState("");
    const [totalElements, setTotalElements] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [arry, setArry] = useState([])

    const isAdmin = localStorage.getItem("isAdmin")
    const [currentPage, setCurrentPage] = useState(0);
    const [size, setSize] = useState(10);
    const allApi = `http://localhost:8080/api/doctor/all?page=${currentPage}&size=${size}`
    const statusApi = `http://localhost:8080/api/doctor/all-by-specialty?page=${currentPage}&size=${size}`


    useEffect(() => {

        const getDoctors = async () => {

            try {
                let response
                if (specialty === "") {
                    response = await axios.get(allApi,
                        {
                            headers: {
                                "Authorization": "Bearer " + localStorage.getItem("token")
                            },
                            params: {
                                word: word
                            }
                        }
                    )
                } else {

                    response = await axios.get(statusApi,
                        {
                            headers: {
                                "Authorization": "Bearer " + localStorage.getItem("token")
                            },
                            params: { specialty: specialty, word: word }
                        }
                    );
                }

                console.log(response.data)
                setTotalElements(response.data.totalElements)
                setTotalPages(response.data.totalPages)
                setDoctors(response.data.doctorsList)
                setArry(Array.from({ length: response.data.totalPages }))
            } catch (err) {
                console.log(JSON.stringify(err))
            }
        }

        const getSpecialties = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/doctor/get-specialties")
                setSpecialtyList(response.data.specialtyList)
            } catch (err) {
                console.log(err?.response?.data)
            }
        }


        getDoctors()
        if (specialtyList.length === 0) {
            getSpecialties()
        }

    }, [currentPage, specialty, word])

    return (

        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >

            <div className="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 className="fw-bold">
                        Doctors List
                    </h2>
                </div>
                {isAdmin &&
                    <button
                        className="btn btn-primary"
                        onClick={() => navigate("/admin/onboard-doctor")}
                    >
                        <i className="bi bi-person-plus me-2"></i>
                        Add Doctor
                    </button>
                }
            </div>

            {/* Icon Legend / Info Banner */}
            <div className="alert alert-info d-flex align-items-center mb-4 py-2 border-0 shadow-sm" role="alert">
                <i className="bi bi-info-circle-fill fs-5 me-3" style={{ color: "var(--sky-600)" }}></i>
                <div className="d-flex flex-wrap gap-4">
                    {isAdmin ? (
                        <>
                            <span className="d-flex align-items-center">
                                <i className="bi bi-pencil-square text-warning me-2 fs-5"></i>
                                <small className="fw-semibold">Edit Doctor</small>
                            </span>
                            <span className="d-flex align-items-center">
                                <i className="bi bi-eye text-primary me-2 fs-5"></i>
                                <small className="fw-semibold">View Availability</small>
                            </span>
                            <span className="d-flex align-items-center">
                                <i className="bi bi-calendar-check-fill text-success me-2 fs-5"></i>
                                <small className="fw-semibold">View Appointments</small>
                            </span>
                        </>
                    ) : (
                        <>
                            <span className="d-flex align-items-center">
                                <i className="bi bi-calendar-plus text-warning me-2 fs-5"></i>
                                <small className="fw-semibold">Book Appointment</small>
                            </span>
                            <span className="d-flex align-items-center">
                                <i className="bi bi-eye text-primary me-2 fs-5"></i>
                                <small className="fw-semibold">View Availability</small>
                            </span>
                        </>
                    )}
                </div>
            </div>

            <div className="card shadow-sm border-0">
                <div className="card-body">

                    <div className="row mb-3">
                        <div className="col-md-10">
                            <div className="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-3">
                                <div className="col-md-4">
                                    <select
                                        className="form-select"
                                        value={specialty}
                                        onChange={(e) => { setSpecialty(e.target.value); setCurrentPage(0); }}
                                    >
                                        <option value="">
                                            All Specialties
                                        </option>
                                        {
                                            specialtyList.map((specialty, index) => (
                                                <option key={index} value={specialty}>
                                                    {specialty}
                                                </option>
                                            ))
                                        }
                                    </select>
                                </div>

                                <div className="col-md-6 w-100" style={{ maxWidth: "500px" }}>
                                    <div className="input-group">
                                        <span className="input-group-text">
                                            <i className="bi bi-search"></i>
                                        </span>
                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Search doctors by name or qualification..."
                                            value={word}
                                            onChange={(e) => {
                                                setWord(e.target.value);
                                                setCurrentPage(0);
                                            }}
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">
                            <thead className="table-primary">
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Specialty</th>
                                    <th>Experience</th>
                                    <th>Qualification</th>
                                    <th>Designation</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {doctors.map((doctor, index) => (
                                    <tr key={index}>
                                        <td>Dr. {doctor.fullname}</td>
                                        <td>{doctor.email}</td>
                                        <td>{doctor.specialty}</td>
                                        <td>{doctor.experience} Years</td>
                                        <td>{doctor.qualification}</td>
                                        <td>{doctor.designation}</td>
                                        <td>
                                            <div className="d-flex">
                                                {isAdmin &&
                                                    <button
                                                        className="btn btn-light-border btn-lg"
                                                        onClick={() =>
                                                            navigate("/admin/update-doctor", { state: doctor })
                                                        }
                                                    >
                                                        <i className="bi bi-pencil-square text-warning"></i>
                                                    </button>
                                                }

                                                <button
                                                    className="btn btn-light-border btn-lg"
                                                    onClick={() =>
                                                        isAdmin ?
                                                            navigate(`/admin/availability/${doctor.id}`) :
                                                            navigate(`/patient/availability/${doctor.id}`)
                                                    }
                                                >
                                                    <i className="bi bi-eye text-primary"></i>
                                                </button>

                                                {!isAdmin &&
                                                    <button
                                                        className="btn btn-light-border btn-lg"
                                                        onClick={() =>
                                                            navigate("/patient/book-appointment1", { state: doctor })
                                                        }
                                                    >
                                                        <i className="bi bi-calendar-plus text-warning"></i>
                                                    </button>
                                                }

                                                {isAdmin &&
                                                    <button
                                                        className="btn btn-light-border btn-lg"
                                                        onClick={() =>
                                                            navigate(`/admin/appointments/${doctor.id}`)
                                                        }
                                                    >
                                                        <i className="bi bi-calendar-check-fill text-success"></i>
                                                    </button>
                                                }
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                                {doctors.length === 0 && (
                                    <tr>
                                        <td colSpan="7" className="text-center py-4 text-muted">
                                            No doctors found.
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    <div className="d-flex flex-wrap justify-content-between align-items-center mt-4 border-top pt-3 gap-3">
                        <div className="fw-semibold text-secondary">
                            Total Doctors: {totalElements}
                        </div>

                        <div className="d-flex align-items-center gap-1">
                            <button
                                className="btn btn-outline-primary rounded-pill"
                                onClick={() => { setCurrentPage(currentPage - 1); }}
                                disabled={currentPage === 0}
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
                                onClick={() => { setCurrentPage(currentPage + 1); }}
                                disabled={totalPages === 0 || currentPage === totalPages - 1}
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

export default ManageDoctor;
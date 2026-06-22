import { useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getAll, deleteFunction } from "../store/action/reportAction";
import { useEffect, useState } from "react";
import axios from "axios";

const MedicalReports = () => {

    const { reports, totalPages, totalElements } = useSelector(state => state.reports);

    console.log(reports)
    const [currentPage, setCurrentPage] = useState(0)
    const [size, setSize] = useState(2)
    const { appid } = useParams()
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const [doDelete, setDoDelete] = useState(null)
    
    const isDoctor = localStorage.getItem("isDoctor")

    useEffect(() => {
        dispatch(getAll(appid, currentPage, size));
    }, [dispatch, currentPage, doDelete]);

    const openFile = async (reportid) => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/report/path/${reportid}`,
                {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    },
                    responseType: "blob"
                }
            );

            const fileURL = URL.createObjectURL(response.data);
            window.open(fileURL, "_blank");

        } catch (err) {
            console.log(err.response)
        }
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

            <div className="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 className="fw-bold mb-1 text-center">
                        My Reports
                    </h2>
                </div>
                
                <button
                    className="btn btn-primary"
                    onClick={() => navigate("/patient/add-report")}
                >
                    <i className="bi bi-plus-lg me-2"></i>
                    Upload Report
                </button>
            </div>

            {/* Confirmation Toast Overlay with Backdrop */}
            {doDelete && (
                <>
                    {/* Dimmed Background Overlay */}
                    <div 
                        className="position-fixed top-0 start-0 w-100 h-100" 
                        style={{ 
                            backgroundColor: "rgba(0, 0, 0, 0.5)", 
                            backdropFilter: "blur(3px)", 
                            zIndex: 1040 
                        }}
                    ></div>

                    {/* Centered Toast */}
                    <div className="position-fixed top-50 start-50 translate-middle p-3" style={{ zIndex: 1050 }}>
                        <div className="toast show align-items-center bg-white border border-warning shadow-lg rounded-4" role="alert" aria-live="assertive" aria-atomic="true">
                            <div className="toast-body text-center p-4">
                                <i className="bi bi-exclamation-triangle-fill text-warning d-block mb-3" style={{ fontSize: "3rem" }}></i>
                                <h5 className="mb-4 text-dark fw-bold">Are you sure you want to delete this report?</h5>
                                <div className="d-flex justify-content-center gap-3">
                                    <button
                                        className="btn btn-danger px-4 fw-semibold"
                                        onClick={() => {
                                            dispatch(deleteFunction(doDelete));
                                            dispatch(getAll(null, currentPage, size));
                                            setDoDelete(null);
                                        }}
                                    >
                                        Yes, Delete
                                    </button>
                                    <button
                                        className="btn btn-secondary px-4 fw-semibold"
                                        onClick={() => setDoDelete(null)}
                                    >
                                        No
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )}

            <div className="card shadow-sm border-0">
                <div className="card-body">

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">
                            <thead className="table-primary">
                                <tr>
                                    <th>Test Name</th>
                                    <th>Date</th>
                                    <th>File Name</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {reports && reports.map((report, index) => (
                                    <tr key={index}>
                                        <td className="fw-semibold text-dark">{report.testName}</td>
                                        <td>{String(report.date).split("-").reverse().join("-")}</td>
                                        <td>
                                            <i className="bi bi-file-earmark-medical-fill text-danger me-2"></i>
                                            {report.fileName}
                                        </td>
                                        <td>
                                            <div className="d-flex gap-2">
                                                <button
                                                    className="btn btn-outline-primary btn-sm"
                                                    onClick={() => { openFile(report.id) }}
                                                >
                                                    <i className="bi bi-eye me-1"></i>
                                                    Open
                                                </button>

                                                {!isDoctor && 
                                                    <>
                                                        <button
                                                            className="btn btn-outline-warning btn-sm"
                                                            onClick={() => navigate(`/patient/update-report/${report.id}`, { state: report })}
                                                        >
                                                            <i className="bi bi-pencil me-1"></i>
                                                            Update
                                                        </button>

                                                        <button
                                                            className="btn btn-outline-danger btn-sm"
                                                            onClick={() => {
                                                                setDoDelete(report.id)
                                                            }}
                                                        >
                                                            <i className="bi bi-trash me-1"></i>
                                                            Delete
                                                        </button>
                                                    </>
                                                }
                                            </div>
                                        </td>
                                    </tr>
                                ))}

                                {/* Empty State Handling */}
                                {(!reports || reports.length === 0) && (
                                    <tr>
                                        <td colSpan="4" className="text-center py-4 text-muted">
                                            No reports found. Click "Upload Report" to add one.
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    {/* Pagination */}
                    <div className="d-flex flex-wrap justify-content-between align-items-center mt-4 border-top pt-3 gap-3">
                        <div className="fw-semibold text-secondary">
                            Total Reports: {totalElements}
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

                            {Array.from({ length: totalPages }).map((_, index) => (
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
                                disabled={totalPages === 0 || currentPage === (totalPages - 1)} 
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

export default MedicalReports;
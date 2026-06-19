
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { getAll } from "../store/action/listAction";

const Task = () => {
    const [currentPage, setCurrentPage] = useState(1)
    const dispatch = useDispatch()
    const { list, totalPages, totalElements } = useSelector(state => state.lists);


    console.log(list)
    console.log(totalPages)
    console.log(totalElements)
    const arry = Array.from({ length: totalPages })

    useEffect(() => {
        dispatch(getAll(currentPage));
    }, [currentPage]);

    return (
        <div className="container mt-3">
            <div className="row mb-3">

            </div>
            <div>
                <h1 className="fw-bold text-center">List</h1>

            </div>
            <div className="fw-semibold text-primary">Total Results: {totalElements}
            </div>

            <div className="row mt-3 mb-3">
                <table className="table table-bordered table-hover ">
                    <thead className="">
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Status</th>
                            <th scope="col">Species</th>
                            <th scope="col">Origin Name</th>
                            <th scope="col">Location Name</th>

                        </tr>
                    </thead>
                    <tbody>
                        {
                            list.map((list, index) => (
                                <tr key={index}>
                                    <td>{list.name}</td>
                                    <td>{list.status}</td>
                                    <td>{list.species}</td>
                                    <td>{list.origin.name}</td>
                                    <td>{list.location.name}</td>
                                </tr>

                            ))
                        }
                    </tbody>
                </table>
            </div>


            <div className="d-flex justify-content-between align-items-center gap-3">


                <div className="d-flex align-items-center gap-1">
                    <button
                        className="btn btn-primary" disabled={currentPage === 1} onClick={() => { setCurrentPage(currentPage - 1) }}
                    >
                        <i className="bi bi-chevron-left"></i> <span className="ms-1">Previous</span>
                    </button>

                    {
                        arry.map((_, index) => (


                            <button
                                key={index} onClick={() => { setCurrentPage(index + 1) }}
                            >
                                {index + 1}
                            </button>
                        ))}

                    <button
                        className="btn btn-primary" disabled={currentPage === (totalPages)}
                        onClick={() => { setCurrentPage(currentPage + 1) }}
                    >
                        <span className="me-1">Next</span>
                        <i className="bi bi-chevron-right"></i>
                    </button>
                </div>


            </div>
            <div className="d-flex">
                <span className="text-primary">


                    Page {currentPage} of {totalPages}
                </span>
            </div>



        </div>
    )

}

export default Task



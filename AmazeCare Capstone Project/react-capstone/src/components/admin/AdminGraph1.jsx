import axios from "axios";
import { useEffect, useState } from "react";
import { Chart } from 'primereact/chart';

const AdminGraph1 = ({ totalDoctors }) => {


    const apiUrl = "http://localhost:8080/api/admin/graph1"
    const [title, setTitle] = useState("");
    const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});


    useEffect(() => {
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }

        }

        const getGraph = async () => {
            try {
                const resp = await axios.get(apiUrl, config)
                console.log(JSON.stringify(resp))
                setTitle(resp.data.title)

                const documentStyle = getComputedStyle(document.documentElement);
                const data = {
                    labels: resp.data.label,
                    datasets: [
                        {
                            data: resp.data.data,
                            backgroundColor: [
                                '#2563EB', // Medical Blue
                                '#14B8A6', // Teal
                                '#22C55E', // Emerald
                                '#8B5CF6', // Violet
                                '#EC4899', // Rose
                                '#F97316', // Orange
                                '#06B6D4', // Sky Cyan
                                '#6366F1', // Indigo
                                '#84CC16', // Lime
                                '#EF4444'  // Soft Red
                            ],

                            hoverBackgroundColor: [
                                '#3B82F6',
                                '#2DD4BF',
                                '#4ADE80',
                                '#A78BFA',
                                '#F472B6',
                                '#FB923C',
                                '#22D3EE',
                                '#818CF8',
                                '#A3E635',
                                '#F87171'
                            ]
                        }
                    ]
                }
                const options = {
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                usePointStyle: true
                            }
                        }
                    }
                };

                setChartData(data);
                setChartOptions(options);


            } catch (err) {
                console.log(JSON.stringify(err))
            }


        }

        getGraph()






    }, [])

    return (

        <div className="col-lg-6">

            <div className="card border-0 shadow-sm">

                <div className="card-body">

                    <h5 className="fw-bold mb-3">
                        {title}
                    </h5>

                    <div className="card flex justify-content-center"  >
                        <Chart type="pie" data={chartData} options={chartOptions} className="w-full md:w-30rem" />
                         <p className="fw-bold text-center mb-3">Total Doctors: {totalDoctors}</p>
                    </div>
                    {/* <div
                                        className="d-flex justify-content-center align-items-center text-muted"
                                        style={{ height: "350px" }}
                                    >
                                        Specialty Bar Graph Here
                                    </div> */}

                </div>

            </div>

        </div>

    )
}
export default AdminGraph1
import React, { useRef, useState } from 'react';

export default function CsvImport({ onImport }) {
    const fileInput = useRef();
    const [error, setError] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const file = fileInput.current.files[0];
        if (!file) {
            setError('Please select a CSV file.');
            return;
        }
        setError('');
        onImport(file);
    };

    return (
        <form onSubmit={handleSubmit} className="row g-2 align-items-center">
            <div className="col-sm-8">
                <input
                    type="file"
                    accept=".csv"
                    ref={fileInput}
                    className="form-control"
                />
            </div>
            <div className="col-sm-4 d-grid">
                <button type="submit" className="btn btn-success">
                    Upload CSV
                </button>
            </div>
            {error && (
                <div className="col-12">
                    <div className="alert alert-danger py-2">{error}</div>
                </div>
            )}
        </form>
    );
}

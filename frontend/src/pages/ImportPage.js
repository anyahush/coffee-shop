import React, { useState } from 'react';
import { importCsv } from '../api/supplyApi';
import CsvImport from '../components/CsvImport';

export default function ImportPage() {
    const [message, setMessage] = useState('');
    const [error, setError]     = useState('');

    const handleImport = (file) => {
        importCsv(file)
            .then(res => {
                setError('');
                setMessage(res.data || 'Import successful.');
            })
            .catch(err => {
                console.error('Import failed', err);
                const msg = err.response?.data || err.message;
                setError(typeof msg === 'string' ? msg : 'Import failed.');
                setMessage('');
            });
    };

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                <div className="col-lg-6">
                    <div className="card shadow-sm">
                        <div className="card-header bg-light">
                            <h5 className="mb-0">Import Supplies from CSV</h5>
                        </div>
                        <div className="card-body">
                            <CsvImport onImport={handleImport} />

                            {message && (
                                <div className="alert alert-success mt-4">
                                    {message}
                                </div>
                            )}
                            {error && (
                                <div className="alert alert-danger mt-4">
                                    {error}
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
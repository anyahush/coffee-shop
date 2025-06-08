// src/pages/ReorderPage.jsx
import React, { useEffect, useState } from 'react';
import { getReorderList } from '../api/supplyApi';
import ReorderList from '../components/ReorderList';

export default function ReorderPage() {
    const [lowStock, setLowStock] = useState([]);

    useEffect(() => {
        getReorderList()
            .then(res => setLowStock(res.data))
            .catch(err => {
                console.error('Failed to load reorder list', err);
                setLowStock([]);
            });
    }, []);

    return (
        <div className="container my-4">
            <h2 className="mb-3">Items to Reorder</h2>
            <ReorderList items={lowStock} />
        </div>
    );
}

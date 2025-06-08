// src/pages/SuppliesPage.jsx
import React, { useEffect, useState } from 'react';
import { getAllSupplies, createSupply, updateSupply, deleteSupply, searchSupplies } from '../api/supplyApi';
import SearchBar from '../components/SearchBar';
import SupplyForm from '../components/SupplyForm';
import SupplyList from '../components/SupplyList';

export default function SuppliesPage() {
    const [items, setItems] = useState([]);
    const [editing, setEditing] = useState(null);

    useEffect(() => { load(); }, []);

    const load = () => getAllSupplies().then(res => setItems(res.data));

    function handleSearch(filters) {
        // no filters? reload all
        if (!filters || Object.keys(filters).length === 0) {
            return load();
        }
        // exactly one key: name/category/supplier
        searchSupplies(filters)
            .then(res => setItems(res.data))
            .catch(err => {
                console.error('Search failed, showing all items', err);
                load();
            });
    }

    const handleSave = item => {
        const call = item.id ? updateSupply(item.id, item) : createSupply(item);
        call.then(() => {
            load();
            setEditing(null);
        });
    };

    const handleDelete = id =>
        deleteSupply(id).then(() => load());

    return (
        <div className="container my-4">
            {/* 1. Search Bar */}
            <div className="row mb-3">
                <div className="col">
                    <SearchBar onSearch={handleSearch} />
                </div>
            </div>

            {/* 2. Add / Edit Form */}
            <div className="row mb-4">
                <div className="col">
                    <div className="card shadow-sm">
                        <div className="card-header bg-light">
                            {editing ? 'Edit Supply Item' : 'Add Supply Item'}
                        </div>
                        <div className="card-body">
                            <SupplyForm
                                key={editing?.id || 'new'}
                                initialData={editing}
                                onSubmit={handleSave}
                                onCancel={()=>setEditing(null)}
                            />
                        </div>
                    </div>
                </div>
            </div>

            {/* 3. Supplies Table */}
            <div className="row">
                <div className="col">
                    <SupplyList
                        items={items}
                        onEdit={item => setEditing(item)}
                        onDelete={handleDelete}
                    />
                </div>
            </div>
        </div>
    );
}

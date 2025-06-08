// src/components/SupplyForm.jsx
import React, { useState, useEffect } from 'react';

export default function SupplyForm({ initialData, onSubmit, onCancel }) {
    // 1) Define your “empty” template
    const emptyForm = {
        name:            '',
        category:        '',
        quantityInStock: '',
        reorderLevel:    '',
        supplierName:    '',
        supplierContact: ''
    };

    // 2) form state, initialize from initialData (for edit) or empty
    const [form, setForm] = useState(() =>
        initialData
            ? {
                name:            initialData.name,
                category:        initialData.category,
                quantityInStock: initialData.quantityInStock.toString(),
                reorderLevel:    initialData.reorderLevel.toString(),
                supplierName:    initialData.supplier?.name || '',
                supplierContact: initialData.supplier?.contactEmail || ''
            }
            : emptyForm
    );

    // 3) Whenever initialData changes (i.e. user clicked “Edit”), re-populate form
    useEffect(() => {
        if (initialData) {
            setForm({
                name:            initialData.name,
                category:        initialData.category,
                quantityInStock: initialData.quantityInStock.toString(),
                reorderLevel:    initialData.reorderLevel.toString(),
                supplierName:    initialData.supplier?.name || '',
                supplierContact: initialData.supplier?.contactEmail || ''
            });
        } else {
            setForm(emptyForm);
        }
    }, [initialData]);

    // 4) Handy handler for all input changes
    const handleChange = e => {
        const { id, value } = e.target;
        setForm(f => ({ ...f, [id]: value }));
    };

    // 5) On save: notify parent, then clear form
    const handleSubmit = e => {
        e.preventDefault();
        onSubmit({
            // convert numbers back to ints
            ...form,
            quantityInStock: parseInt(form.quantityInStock, 10),
            reorderLevel:    parseInt(form.reorderLevel, 10),
            supplier: {
                name:          form.supplierName.trim(),
                contactEmail:  form.supplierContact.trim()
            }
        });
        setForm(emptyForm);
    };

    // 6) On cancel: clear + notify parent
    const handleCancel = () => {
        setForm(emptyForm);
        onCancel();
    };

    return (
        <form onSubmit={handleSubmit} className="row g-2 align-items-end">
            {/* Name */}
            <div className="col">
                <label htmlFor="name" className="form-label">Name</label>
                <input
                    id="name"
                    type="text"
                    className="form-control"
                    value={form.name}
                    onChange={handleChange}
                    required
                />
            </div>

            {/* Category */}
            <div className="col">
                <label htmlFor="category" className="form-label">Category</label>
                <input
                    id="category"
                    type="text"
                    className="form-control"
                    value={form.category}
                    onChange={handleChange}
                    required
                />
            </div>

            {/* Qty */}
            <div className="col">
                <label htmlFor="quantityInStock" className="form-label">Qty</label>
                <input
                    id="quantityInStock"
                    type="number"
                    min="0"
                    className="form-control"
                    value={form.quantityInStock}
                    onChange={handleChange}
                    required
                />
            </div>

            {/* Reorder */}
            <div className="col">
                <label htmlFor="reorderLevel" className="form-label">Reorder level</label>
                <input
                    id="reorderLevel"
                    type="number"
                    min="0"
                    className="form-control"
                    value={form.reorderLevel}
                    onChange={handleChange}
                    required
                />
            </div>

            {/* Supplier Name */}
            <div className="col">
                <label htmlFor="supplierName" className="form-label">Supplier</label>
                <input
                    id="supplierName"
                    type="text"
                    className="form-control"
                    value={form.supplierName}
                    onChange={handleChange}
                />
            </div>

            {/* Supplier Contact */}
            <div className="col">
                <label htmlFor="supplierContact" className="form-label">Supplier Contact</label>
                <input
                    id="supplierContact"
                    type="email"
                    className="form-control"
                    value={form.supplierContact}
                    onChange={handleChange}
                />
            </div>

            {/* Buttons */}
            <div className="col-auto">
                <button type="submit" className="btn btn-primary me-2">
                    Save
                </button>
                <button
                    type="button"
                    className="btn btn-outline-secondary"
                    onClick={handleCancel}
                >
                    Cancel
                </button>
            </div>
        </form>
    );
}

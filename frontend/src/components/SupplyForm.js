import React, { useState, useEffect } from 'react';

export default function SupplyForm({ initialData = {}, onSubmit, onCancel }) {
    // flatten nested supplier into local state
    const [form, setForm] = useState({
        id:                null,
        name:              '',
        category:          '',
        quantityInStock:   '',
        reorderLevel:      '',
        supplierName:      '',
        supplierContact:   '',
    });

    // sync in editing data
    useEffect(() => {
        if (initialData) {
            setForm({
                id:                initialData.id ?? null,
                name:              initialData.name || '',
                category:          initialData.category || '',
                quantityInStock:   initialData.quantityInStock ?? '',
                reorderLevel:      initialData.reorderLevel ?? '',
                supplierName:      initialData.supplier?.name    || '',
                supplierContact:   initialData.supplier?.contact || '',
            });
        }
    }, [initialData]);

    const handleChange = e => {
        const { id, value } = e.target;
        setForm(f => ({ ...f, [id]: value }));
    };

    const handleSubmit = e => {
        e.preventDefault();
        onSubmit({
            id:               form.id,
            name:             form.name,
            category:         form.category,
            quantityInStock:  Number(form.quantityInStock),
            reorderLevel:     Number(form.reorderLevel),
            supplier: {
                name:    form.supplierName,
                contact: form.supplierContact,
            }
        });
    };

    return (
        <form onSubmit={handleSubmit} className="d-flex flex-wrap align-items-end gx-3 gy-2">
            {[
                { id: 'name',            label: 'Name',            width: '150px' },
                { id: 'category',        label: 'Category',        width: '150px' },
                { id: 'quantityInStock', label: 'Qty',             width: '80px',  type: 'number' },
                { id: 'reorderLevel',    label: 'Reorder level',   width: '100px', type: 'number' },
                { id: 'supplierName',    label: 'Supplier Name',   width: '180px' },
                { id: 'supplierContact', label: 'Supplier Contact',width: '200px' },
            ].map(({ id, label, width, type = 'text' }) => (
                <div key={id} className="d-flex flex-column">
                    <label htmlFor={id} className="form-label mb-1">{label}</label>
                    <input
                        id={id}
                        type={type}
                        className="form-control"
                        style={{ width }}
                        value={form[id]}
                        onChange={handleChange}
                    />
                </div>
            ))}

            <div className="d-flex align-items-center ms-3">
                <button type="submit" className="btn btn-primary me-2">
                    Save
                </button>
                {onCancel && (
                    <button
                        type="button"
                        className="btn btn-outline-danger"
                        onClick={onCancel}
                    >
                        Cancel
                    </button>
                )}
            </div>
        </form>
    );
}

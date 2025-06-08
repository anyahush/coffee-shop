import { useState, useEffect } from 'react';

export default function SupplyForm({ initialData = null, onSubmit, onCancel }) {
    const [formData, setFormData] = useState({
        name: '',
        category: '',
        quantityInStock: '',
        reorderLevel: '',
        supplierName: ''
    });

    useEffect(() => {
        if (initialData) {
            setFormData(initialData);
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Convert quantity fields to numbers
        const payload = {
            ...formData,
            quantityInStock: Number(formData.quantityInStock),
            reorderLevel: Number(formData.reorderLevel)
        };

        onSubmit(payload);
        setFormData({ name: '', category: '', quantityInStock: '', reorderLevel: '', supplierName: '' });
    };

    return (
        <form onSubmit={handleSubmit} style={{ marginBottom: '2rem' }}>
            <p><strong>{initialData ? 'Edit' : 'Add'} Supply</strong></p>

            <input
                name="name"
                placeholder="Name"
                value={formData.name}
                onChange={handleChange}
                required
            />{' '}
            <input
                name="category"
                placeholder="Category"
                value={formData.category}
                onChange={handleChange}
                required
            />{' '}
            <input
                name="quantityInStock"
                placeholder="Qty"
                type="number"
                value={formData.quantityInStock}
                onChange={handleChange}
                required
            />{' '}
            <input
                name="reorderLevel"
                placeholder="Reorder level"
                type="number"
                value={formData.reorderLevel}
                onChange={handleChange}
                required
            />{' '}
            <input
                name="supplierName"
                placeholder="Supplier"
                value={formData.supplierName}
                onChange={handleChange}
                required
            />{' '}

            <button type="submit">Save</button>{' '}
            <button type="button" onClick={onCancel}>Cancel</button>
        </form>
    );
}
